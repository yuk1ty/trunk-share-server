package route.driver

import com.twitter.finagle.http.{Status}
import domain.{Driver}
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
    val drivers = driverRepository.getAll()
    Output.payload(drivers, Status.Accepted)
  }

  def create(): Endpoint[Driver] =
    post("drivers" :: "new" :: jsonBody[Driver]) { driver: Driver =>
      driverRepository.save(driver)
      Ok(driver)
    }
}

object DriversEndpoint
    extends DriversEndpoint
    with MixInDriverUsecase
    with MixInDriverRepository
