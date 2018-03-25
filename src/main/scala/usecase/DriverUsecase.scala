package usecase

import domain.{Driver, DriverRating}

trait DriverUsecase {
  def updateDriverRating(rating: DriverRating, driver: Driver): Driver = {
    val driverRate = if (rating.safety < 30) {
      5
    } else if (rating.safety >= 30 && rating.safety < 70) {
      4
    } else if (rating.safety >= 70 && rating.safety < 110) {
      3
    } else if (rating.safety >= 150 && rating.safety < 190) {
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
