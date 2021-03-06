package repository

import com.twitter.finagle.mysql.{IntValue, Row, StringValue}
import com.twitter.util.Future
import context.OemKey
import domain.{LuggageType, Sender}
import infra._
import org.apache.commons.lang.BooleanUtils
import skinny.http.Request

trait SenderRepository extends UsesMySqlAdapter with UsesMinikuraAdapter {

  private lazy val insertQuery =
    """
      |insert into sender (
      |  id,
      |  name,
      |  where_from,
      |  where_to,
      |  start_at,
      |  end_at,
      |  luggage_type,
      |  fragile)
      | values (?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  object SenderMapper extends EntityMapper[Sender] {
    override def apply(row: Row): Sender = {
      val IntValue(id) = row("id").get
      val StringValue(name) = row("name").get
      val StringValue(whereFrom) = row("where_from").get
      val StringValue(whereTo) = row("where_to").get
      val StringValue(startAt) = row("start_at").get
      val StringValue(endAt) = row("end_at").get
      val StringValue(luggageType) = row("luggage_type").get
      val IntValue(fragile) = row("fragile").get
      Sender(
        id,
        name,
        whereFrom,
        whereTo,
        startAt,
        endAt,
        LuggageType.parse(luggageType),
        BooleanUtils.toBoolean(fragile)
      )
    }
  }

  def getAll(): Future[Seq[Sender]] = {
    mySqlAdapter.client.select("select * from sender")(SenderMapper)
  }

  def save(sender: Sender): Future[Unit] = {
    val minikuraRequet =
      Request("https://junction-tokyo.minikura.com/v1/minikura/storing")
        .queryParam(("oem_key", OemKey.key))
        .queryParam(("customer_id", sender.id))
        .queryParam(("privacy_status", "public"))
        .queryParam(("common01", sender.whereFrom))
        .queryParam(("common02", sender.whereTo))
        .queryParam(("common03", sender.luggageType))
        .queryParam(("common04", sender.fragile))
    val store2MinikuraTask = minikuraAdapter.send(minikuraRequet)

    val store2RDSTask = mySqlAdapter.client.prepare(insertQuery)
    store2MinikuraTask map { response =>
      println(response.asString)
      store2RDSTask(
        sender.id,
        sender.name,
        sender.whereFrom,
        sender.whereTo,
        sender.startAt,
        sender.endAt,
        sender.luggageType.stringify,
        if (sender.fragile) 1 else 0
      ).unit
    }
  }
}

object SenderRepository
    extends SenderRepository
    with MixInMySqlAdapter
    with MixInMinikuraAdapter

trait UsesSenderRepository {
  val senderRepository: SenderRepository
}

trait MixInSenderRepository {
  val senderRepository: SenderRepository = SenderRepository
}
