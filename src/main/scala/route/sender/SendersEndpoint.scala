package route.sender

import com.twitter.finagle.http.Status
import domain.Sender
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import io.finch.syntax._
import io.finch.Endpoint
import repository.{MixInSenderRepository, UsesSenderRepository}

trait SendersEndpoint extends UsesSenderRepository {

  def apply() = all() :+: create()

  def all(): Endpoint[Seq[Sender]] = get("senders") {
    senderRepository.getAll() map { senders =>
      Output
        .payload(senders, Status.Accepted)
        .withHeader(("Access-Control-Allow-Origin", "*"))
    }
  }

  def create(): Endpoint[Sender] =
    post("senders" :: "new" :: jsonBody[Sender]) { (sender: Sender) =>
      {
        senderRepository.save(sender)
        println(s"保存に成功しました: $sender")
        Output
          .payload(sender, Status.Accepted)
          .withHeader(("Access-Control-Allow-Origin", "*"))
          .withHeader(("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS"))
          .withHeader(("Access-Control-Allow-Credentials", "true"))

      }
    }
}

object SendersEndpoint extends SendersEndpoint with MixInSenderRepository
