package repository

import infra.{MixInMySqlAdapter, UsesMySqlAdapter}

trait DriverRepository extends UsesMySqlAdapter {

  // connection testing method
  def test(): Unit = {
    mySqlAdapter.client.select("select * from driver")(row => row.values)
  }
}

object DriverRepository extends DriverRepository with MixInMySqlAdapter

trait UsesDriverRepository {
  val driverRepository: DriverRepository
}

trait MixInDriverRepository {
  val driverRepository: DriverRepository = DriverRepository
}
