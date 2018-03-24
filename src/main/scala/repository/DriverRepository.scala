package repository

trait DriverRepository {}

object DriverRepository extends DriverRepository

trait UsesDriverRepository {
  val driverRepository: DriverRepository
}

trait MixInDriverRepository {
  val driverRepository: DriverRepository = DriverRepository
}
