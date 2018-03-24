package route.hc

import io.finch.Endpoint
import io.finch._
import io.finch.syntax._

trait HealthCheckEndpoint {

  def apply(): Endpoint[String] = get("hc") {
    Ok("OK")
  }
}

object HealthCheckEndpoint extends HealthCheckEndpoint