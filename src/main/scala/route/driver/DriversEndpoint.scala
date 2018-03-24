package route.driver

import com.twitter.finagle.http.Status
import domain.{ByMotorcycle, Driver, Weekdays}
import io.finch._
import io.finch.syntax._
import io.finch.{Endpoint, Output}
import usecase.{MixInDriverUsecase, UsesDriverUsecase}

trait DriversEndpoint extends UsesDriverUsecase {

  def apply() = all() :+: create()

  def all(): Endpoint[Seq[Driver]] = get("drivers") {
    Output.payload(
      Seq(
        Driver(id = 1,
               whereFrom = "Saitama",
               whereTo = "Tokyo",
               startAt = "08:00",
               endAt = "09:00",
               rating = 1,
               commutionType = ByMotorcycle,
               frequency = Weekdays)),
      Status.Accepted
    )
  }

  def create(): Endpoint[Seq[Driver]] = get("drivers" :: "new") {
    Output.payload(
      Seq(
        Driver(id = 1,
               whereFrom = "Saitama",
               whereTo = "Tokyo",
               startAt = "08:00",
               endAt = "09:00",
               rating = 1,
               commutionType = ByMotorcycle,
               frequency = Weekdays)),
      Status.Accepted
    )
  }
}

object DriversEndpoint extends DriversEndpoint with MixInDriverUsecase
