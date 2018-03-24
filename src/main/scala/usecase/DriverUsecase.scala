package usecase

trait DriverUsecase {}

object DriverUsecase extends DriverUsecase

trait UsesDriverUsecase {
  val driverUsecase: DriverUsecase
}

trait MixInDriverUsecase {
  val driverUsecase: DriverUsecase = DriverUsecase
}
