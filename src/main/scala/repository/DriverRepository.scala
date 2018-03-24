package repository

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
             whereFrom = "Saitama",
             whereTo = "Tokyo",
             startAt = "08:00",
             endAt = "09:00",
             rating = 1,
             commutionType = ByMotorcycle,
             frequency = Weekdays),
      Driver(id = 2,
             whereFrom = "Saitama",
             whereTo = "Tokyo",
             startAt = "08:00",
             endAt = "09:00",
             rating = 1,
             commutionType = ByMotorcycle,
             frequency = Weekdays),
      Driver(id = 3,
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
}

object DriverRepository extends DriverRepository with MixInMySqlAdapter

trait UsesDriverRepository {
  val driverRepository: DriverRepository
}

trait MixInDriverRepository {
  val driverRepository: DriverRepository = DriverRepository
}
