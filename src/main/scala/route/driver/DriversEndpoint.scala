package route.driver

import com.twitter.finagle.http.Status
import com.typesafe.scalalogging.LazyLogging
import domain._
import io.finch._
import io.finch.circe._
import io.circe.generic.auto._
import io.finch.syntax._
import io.finch.{Endpoint, Output}
import repository.{MixInDriverRepository, UsesDriverRepository}
import usecase.{MixInDriverUsecase, UsesDriverUsecase}

trait DriversEndpoint
    extends UsesDriverUsecase
    with UsesDriverRepository
    with LazyLogging {

  def apply() = all() :+: create() :+: update() :+: mock() :+: createOp()

  def mock(): Endpoint[Seq[Driver]] = get("drivers" :: "mock") {
    Output.payload(
      Seq[Driver](
        Driver(1,
               "Toyoda",
               "Tokyo",
               "Chiba",
               "08:00",
               "09:00",
               5,
               ByCar,
               Holidays),
        Driver(2,
               "Ishizaki",
               "Tokyo",
               "Chiba",
               "08:00",
               "09:00",
               5,
               ByMotorcycle,
               Holidays),
        Driver(3,
               "Narasaki",
               "Tokyo",
               "Chiba",
               "08:00",
               "09:00",
               5,
               ByBicycle,
               Holidays)
      ))
  }

  def all(): Endpoint[Seq[Driver]] = get("drivers") {
    driverRepository.getAll() map { drivers =>
      Output
        .payload(drivers, Status.Accepted)
        .withHeader(("Access-Control-Allow-Origin", "*"))
    }
  }

  def create(): Endpoint[Driver] =
    post("drivers" :: "new" :: jsonBody[Driver]) { driver: Driver =>
      driverRepository.save(driver)
      println(s"保存に成功しました: $driver")
      Output
        .payload(driver, Status.Accepted)
        .withHeader(("Access-Control-Allow-Origin", "*"))
        .withHeader(("Access-Control-Allow-Methods", "POST,HEAD,OPTIONS"))
        .withHeader(("Access-Control-Allow-Credentials", "true"))
        .withHeader(
          ("Access-Control-Allow-Headers",
           "Accept, Accept-CH, Accept-Charset, Accept-Datetime, Accept-Encoding, Accept-Ext, Accept-Features, Accept-Language, Accept-Params, Accept-Ranges, Access-Control-Allow-Credentials, Access-Control-Allow-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Expose-Headers, Access-Control-Max-Age, Access-Control-Request-Headers, Access-Control-Request-Method, Age, Allow, Alternates, Authentication-Info, Authorization, C-Ext, C-Man, C-Opt, C-PEP, C-PEP-Info, CONNECT, Cache-Control, Compliance, Connection, Content-Base, Content-Disposition, Content-Encoding, Content-ID, Content-Language, Content-Length, Content-Location, Content-MD5, Content-Range, Content-Script-Type, Content-Security-Policy, Content-Style-Type, Content-Transfer-Encoding, Content-Type, Content-Version, Cookie, Cost, DAV, DELETE, DNT, DPR, Date, Default-Style, Delta-Base, Depth, Derived-From, Destination, Differential-ID, Digest, ETag, Expect, Expires, Ext, From, GET, GetProfile, HEAD, HTTP-date, Host, IM, If, If-Match, If-Modified-Since, If-None-Match, If-Range, If-Unmodified-Since, Keep-Alive, Label, Last-Event-ID, Last-Modified, Link, Location, Lock-Token, MIME-Version, Man, Max-Forwards, Media-Range, Message-ID, Meter, Negotiate, Non-Compliance, OPTION, OPTIONS, OWS, Opt, Optional, Ordering-Type, Origin, Overwrite, P3P, PEP, PICS-Label, POST, PUT, Pep-Info, Permanent, Position, Pragma, ProfileObject, Protocol, Protocol-Query, Protocol-Request, Proxy-Authenticate, Proxy-Authentication-Info, Proxy-Authorization, Proxy-Features, Proxy-Instruction, Public, RWS, Range, Referer, Refresh, Resolution-Hint, Resolver-Location, Retry-After, Safe, Sec-Websocket-Extensions, Sec-Websocket-Key, Sec-Websocket-Origin, Sec-Websocket-Protocol, Sec-Websocket-Version, Security-Scheme, Server, Set-Cookie, Set-Cookie2, SetProfile, SoapAction, Status, Status-URI, Strict-Transport-Security, SubOK, Subst, Surrogate-Capability, Surrogate-Control, TCN, TE, TRACE, Timeout, Title, Trailer, Transfer-Encoding, UA-Color, UA-Media, UA-Pixels, UA-Resolution, UA-Windowpixels, URI, Upgrade, User-Agent, Variant-Vary, Vary, Version, Via, Viewport-Width, WWW-Authenticate, Want-Digest, Warning, Width, X-Content-Duration, X-Content-Security-Policy, X-Content-Type-Options, X-CustomHeader, X-DNSPrefetch-Control, X-Forwarded-For, X-Forwarded-Port, X-Forwarded-Proto, X-Frame-Options, X-Modified, X-OTHER, X-PING, X-PINGOTHER, X-Powered-By, X-Requested-With"))
        .withHeader(("Content-Type", "application/json; charset=utf8"))
    }

  def createOp(): Endpoint[String] = options("drivers" :: "new") {
    Ok("OK")
  }

  def update(): Endpoint[Driver] =
    patch("drivers" :: "rating" :: "update" :: jsonBody[DriverRating]) {
      rating: DriverRating =>
        driverRepository.findOne(rating.driverId) map {
          case Some(driver) =>
            val update = driverUsecase.updateDriverRating(rating, driver)
            driverRepository.update(update)
            println(s"評価変更以前のドライバー: ${driver}, 評価更新後のドライバー: ${update}")
            Ok(update)
              .withHeader(("Access-Control-Allow-Origin", "*"))
              .withHeader(
                ("Access-Control-Allow-Methods", "POST,PATCH,HEAD,OPTIONS"))
              .withHeader(("Access-Control-Allow-Credentials", "true"))
              .withHeader(("Access-Control-Allow-Headers", "*"))
          case None => {
            println("ID に該当するドライバーがいませんでした．")
            NoContent
              .withHeader(("Access-Control-Allow-Origin", "*"))
              .withHeader(
                ("Access-Control-Allow-Methods", "POST,PATCH,HEAD,OPTIONS"))
              .withHeader(("Access-Control-Allow-Credentials", "true"))
              .withHeader(("Access-Control-Allow-Headers", "*"))
          }
        }
    }
}

object DriversEndpoint
    extends DriversEndpoint
    with MixInDriverUsecase
    with MixInDriverRepository
