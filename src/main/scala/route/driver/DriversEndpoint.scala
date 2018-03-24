package route.driver

import com.twitter.finagle.http.Status
import com.typesafe.scalalogging.LazyLogging
import domain._
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import io.finch.syntax._
import io.finch.{Endpoint, Output}
import repository.{MixInDriverRepository, UsesDriverRepository}
import usecase.{MixInDriverUsecase, UsesDriverUsecase}

trait DriversEndpoint
    extends UsesDriverUsecase
    with UsesDriverRepository
    with LazyLogging {

  def apply() = all() :+: create() :+: update() :+: mock()

  def mock(): Endpoint[Seq[Driver]] = get("drivers/mock") {
    Output.payload(
      Seq[Driver](
        Driver(1,
               "Toyoda",
               "Tokyo",
               "Chiba",
               "08:00",
               "09:00",
               5,
               ByCar,
               Holidays),
        Driver(2,
               "Ishizaki",
               "Tokyo",
               "Chiba",
               "08:00",
               "09:00",
               5,
               ByMotorcycle,
               Holidays),
        Driver(3,
               "Narasaki",
               "Tokyo",
               "Chiba",
               "08:00",
               "09:00",
               5,
               ByBicycle,
               Holidays)
      ))
  }

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
      println(s"保存に成功しました: $driver")
      Ok(driver).withHeader(("Access-Control-Allow-Origin", "*"))
    }

  def update(): Endpoint[Driver] =
    patch("drivers" :: "rating" :: "update" :: jsonBody[DriverRating]) {
      rating: DriverRating =>
        driverRepository.findOne(rating.driverId) map {
          case Some(driver) =>
            val update = driverUsecase.updateDriverRating(rating, driver)
            driverRepository.update(update)
            println(s"アップデートできました: $update")
            Ok(update)
              .withHeader(("Access-Control-Allow-Origin", "*"))
          case None => {
            println("ID に該当するドライバーがいませんでした．")
            NoContent.withHeader(("Access-Control-Allow-Origin", "*"))
          }
        }
    }
}

object DriversEndpoint
    extends DriversEndpoint
    with MixInDriverUsecase
    with MixInDriverRepository
