package repository

import com.twitter.finagle.mysql.{IntValue, Row, StringValue}
import com.twitter.util.Future
import domain.{LuggageType, Sender}
import infra.{EntityMapper, MixInMySqlAdapter, UsesMySqlAdapter}
import org.apache.commons.lang.BooleanUtils

trait SenderRepository extends UsesMySqlAdapter {

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
    val ps = mySqlAdapter.client.prepare(insertQuery)
    ps(
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

object SenderRepository extends SenderRepository with MixInMySqlAdapter

trait UsesSenderRepository {
  val senderRepository: SenderRepository
}

trait MixInSenderRepository {
  val senderRepository: SenderRepository = SenderRepository
}
