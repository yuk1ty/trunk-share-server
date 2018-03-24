package usecase

import domain.{Driver, DriverRating}

trait DriverUsecase {
  def updateDriverRating(rating: DriverRating, driver: Driver): Driver = {
    val driverRate = if (rating.safety < 10) {
      5
    } else if (rating.safety >= 10 && rating.safety < 20) {
      4
    } else if (rating.safety >= 20 && rating.safety < 30) {
      3
    } else if (rating.safety >= 30 && rating.safety < 40) {
      2
    } else {
      1
    }

    driver.copy(rating = driverRate)
  }
}

object DriverUsecase extends DriverUsecase

trait UsesDriverUsecase {
  val driverUsecase: DriverUsecase
}

trait MixInDriverUsecase {
  val driverUsecase: DriverUsecase = DriverUsecase
}
