package infra

import com.twitter.util.Future
import skinny.http.{HTTP, Request, Response}

trait MinikuraAdapter {

  def send(body: Array[Byte]): Future[Response] = {
    Future.value(
      HTTP.post(
        Request(
          "https://junction-tokyo.minikura.com/v1/minikura/storing?oem_key=98e7b2ea4d1ec5dc")
          .body(body)))
  }
}

object MinikuraAdapter extends MinikuraAdapter

trait UsesMinikuraAdapter {
  val minikuraAdapter: MinikuraAdapter
}

trait MixInMinikuraAdapter {
  val minikuraAdapter: MinikuraAdapter = MinikuraAdapter
}
