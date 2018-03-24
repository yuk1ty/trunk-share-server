package domain

sealed trait CommutionType {
  import io.circe._
  import io.circe.generic.semiauto._

  implicit val encode: Encoder[CommutionType] = deriveEncoder[CommutionType]
  implicit val decode: Decoder[CommutionType] = deriveDecoder[CommutionType]
}

case object ByMotorcycle extends CommutionType

case object ByCar extends CommutionType

case object ByBicycle extends CommutionType