package usecase

import domain.{Driver, DriverRating}

trait DriverUsecase {
  def updateDriverRating(rating: DriverRating, driver: Driver): Driver = {
    driver.copy(rating = rating.safety.toInt)
  }
}

object DriverUsecase extends DriverUsecase

trait UsesDriverUsecase {
  val driverUsecase: DriverUsecase
}

trait MixInDriverUsecase {
  val driverUsecase: DriverUsecase = DriverUsecase
}
