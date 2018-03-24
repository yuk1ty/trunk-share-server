package route.driver

import com.twitter.finagle.http.Status
import domain.Driver
import io.finch.syntax._
import io.finch.{Endpoint, Output}
import usecase.{MixInDriverUsecase, UsesDriverUsecase}

trait DriversEndpoint extends UsesDriverUsecase {

  def apply(): Endpoint[Seq[Driver]] = get("drivers") {
    Output.payload(Seq(Driver(1L, "Tokyo", "Saitama", 3)), Status.Accepted)
  }
}

object DriversEndpoint extends DriversEndpoint with MixInDriverUsecase
