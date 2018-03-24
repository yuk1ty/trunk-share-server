import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import route.driver.DriversEndpoint
import route.hc.HealthCheckEndpoint

object TrunkShareServer {

  def main(args: Array[String]): Unit = {
    val apis = HealthCheckEndpoint() :+: DriversEndpoint()

    val server = Http.server.serve(":8888", apis.toServiceAs[Application.Json])

    try {
      Await.ready(server)
    } finally {
      server.close()
    }
  }
}
