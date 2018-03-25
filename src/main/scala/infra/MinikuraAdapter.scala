package infra

import com.twitter.util.Future
import skinny.http.{HTTP, Request, Response}

trait MinikuraAdapter {

  def send(req: Request): Future[Response] = {
    Future.value(HTTP.post(req))
  }
}

object MinikuraAdapter extends MinikuraAdapter

trait UsesMinikuraAdapter {
  val minikuraAdapter: MinikuraAdapter
}

trait MixInMinikuraAdapter {
  val minikuraAdapter: MinikuraAdapter = MinikuraAdapter
}
