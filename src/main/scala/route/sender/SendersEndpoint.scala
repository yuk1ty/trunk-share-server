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
      Output.payload(senders, Status.Accepted)
    }
  }

  def create(): Endpoint[Sender] =
    post("senders" :: "new" :: jsonBody[Sender]) { (sender: Sender) =>
      {
        senderRepository.save(sender)
        Output.payload(sender, Status.Accepted)
      }
    }
}

object SendersEndpoint extends SendersEndpoint with MixInSenderRepository
