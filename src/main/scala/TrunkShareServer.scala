import com.twitter.finagle.Http
import com.twitter.finagle.http.filter.Cors
import com.twitter.util.Await
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import route.driver.DriversEndpoint
import route.hc.HealthCheckEndpoint

object TrunkShareServer {

  def main(args: Array[String]): Unit = {
    val apis = HealthCheckEndpoint() :+: DriversEndpoint()

    val service = apis.toServiceAs[Application.Json]

    val corsPolicy = Cors.Policy(
      allowsOrigin = _ => Some("*"),
      allowsMethods = _ => Some(Seq("GET", "POST", "PATCH", "DELETE")),
      allowsHeaders = _ => Some(Seq("Accept"))
    )

    val corsServer = new Cors.HttpFilter(corsPolicy).andThen(service)

    val server = Http.server
      .serve(":8888", corsServer)

    try {
      Await.ready(server)
    } finally {
      server.close()
    }
  }
}
