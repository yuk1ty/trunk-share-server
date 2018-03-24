package route.driver

import com.twitter.finagle.http.Status
import domain.{Driver, DriverRating}
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import io.finch.syntax._
import io.finch.{Endpoint, Output}
import repository.{MixInDriverRepository, UsesDriverRepository}
import usecase.{MixInDriverUsecase, UsesDriverUsecase}

trait DriversEndpoint extends UsesDriverUsecase with UsesDriverRepository {

  def apply() = all() :+: create()

  def all(): Endpoint[Seq[Driver]] = get("drivers") {
    driverRepository.getAll() map { drivers =>
      Output
        .payload(drivers, Status.Accepted)
        .withHeader(("Access-Control-Allow-Origin", "*"))
    }
  }

  def create(): Endpoint[Driver] =
    post("drivers" :: "new" :: jsonBody[Driver]) { driver: Driver =>
      driverRepository.save(driver)
      Ok(driver).withHeader(("Access-Control-Allow-Origin", "*"))
    }

  def update(): Endpoint[Driver] =
    patch("drivers" :: "rating" :: "update" :: jsonBody[DriverRating]) {
      rating: DriverRating =>
        driverRepository.findOne(rating.driverId) map {
          case Some(driver) =>
            Ok(driverUsecase.updateDriverRating(rating, driver))
              .withHeader(("Access-Control-Allow-Origin", "*"))
          case None =>
            NoContent.withHeader(("Access-Control-Allow-Origin", "*"))
        }
    }
}

object DriversEndpoint
    extends DriversEndpoint
    with MixInDriverUsecase
    with MixInDriverRepository
