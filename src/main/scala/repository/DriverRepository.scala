package repository

import com.twitter.util.Future
import domain.{ByMotorcycle, Driver, Weekdays}
import infra.{MixInMySqlAdapter, UsesMySqlAdapter}

trait DriverRepository extends UsesMySqlAdapter {

  // connection testing method
  def test(): Unit = {
    mySqlAdapter.client.select("select * from driver")(row => row.values)
  }

  def getAll(): Seq[Driver] = {
    // TODO あとで MySQL につなげる
    Seq[Driver](
      Driver(id = 1,
             name = "A",
             whereFrom = "Saitama",
             whereTo = "Tokyo",
             startAt = "08:00",
             endAt = "09:00",
             rating = 1,
             commutionType = ByMotorcycle,
             frequency = Weekdays),
      Driver(id = 2,
             name = "B",
             whereFrom = "Saitama",
             whereTo = "Tokyo",
             startAt = "08:00",
             endAt = "09:00",
             rating = 1,
             commutionType = ByMotorcycle,
             frequency = Weekdays),
      Driver(id = 3,
             name = "C",
             whereFrom = "Saitama",
             whereTo = "Tokyo",
             startAt = "08:00",
             endAt = "09:00",
             rating = 1,
             commutionType = ByMotorcycle,
             frequency = Weekdays)
    )
//    mySqlAdapter.client.select("select * from driver")
  }

  def save(driver: Driver): Future[Unit] = {
    Future(println("保存しました"))
  }
}

object DriverRepository extends DriverRepository with MixInMySqlAdapter

trait UsesDriverRepository {
  val driverRepository: DriverRepository
}

trait MixInDriverRepository {
  val driverRepository: DriverRepository = DriverRepository
}
