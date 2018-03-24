package repository

import com.twitter.finagle.mysql.{IntValue, Row, StringValue}
import com.twitter.util.Future
import domain._
import infra.{EntityMapper, MixInMySqlAdapter, UsesMySqlAdapter}

trait DriverRepository extends UsesMySqlAdapter {

  private lazy val insertQuery =
    """
      |insert into driver (id, name, where_from, where_to, start_at, end_at, rating, commution_type, frequency)
      | values (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.stripMargin

  object DriverMapper extends EntityMapper[Driver] {
    override def apply(row: Row): Driver = {
      val IntValue(id) = row("id").get
      val StringValue(name) = row("name").get
      val StringValue(whereFrom) = row("where_from").get
      val StringValue(whereTo) = row("where_to").get
      val StringValue(startAt) = row("start_at").get
      val StringValue(endAt) = row("end_at").get
      val IntValue(rating) = row("rating").get
      val StringValue(commutionType) = row("commution_type").get
      val StringValue(frequency) = row("frequency").get
      Driver(id,
             name,
             whereFrom,
             whereTo,
             startAt,
             endAt,
             rating,
             CommutionType.parse(commutionType),
             Frequency.parse(frequency))

    }
  }

  // connection testing method
  def test(): Unit = {
    mySqlAdapter.client.select("select * from driver")(DriverMapper)
  }

  def getAll(): Future[Seq[Driver]] = {
    mySqlAdapter.client.select("select * from driver")(DriverMapper)
  }

  def save(driver: Driver): Future[Unit] = {
    val ps = mySqlAdapter.client.prepare(insertQuery)
    ps(
      driver.id,
      driver.name,
      driver.whereFrom,
      driver.whereTo,
      driver.startAt,
      driver.endAt,
      driver.rating,
      driver.commutionType.stringify,
      driver.frequency.stringify
    ).unit
  }
}

object DriverRepository extends DriverRepository with MixInMySqlAdapter

trait UsesDriverRepository {
  val driverRepository: DriverRepository
}

trait MixInDriverRepository {
  val driverRepository: DriverRepository = DriverRepository
}
