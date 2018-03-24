import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import route.driver.DriversEndpoint
import route.hc.HealthCheckEndpoint
import route.sender.SendersEndpoint

object TrunkShareServer {

  def main(args: Array[String]): Unit = {
    val apis = HealthCheckEndpoint() :+: DriversEndpoint() :+: SendersEndpoint()

    val service = apis.toServiceAs[Application.Json]

    val server = Http.server.serve(":8888", service)

    try {
      Await.ready(server)
    } finally {
      server.close()
    }
  }
}
