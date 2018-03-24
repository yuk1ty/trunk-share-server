package domain

import infra.MySqlEnum

sealed trait CommutionType extends MySqlEnum {
  import io.circe._
  import io.circe.generic.semiauto._

  implicit val encode: Encoder[CommutionType] = deriveEncoder[CommutionType]
  implicit val decode: Decoder[CommutionType] = deriveDecoder[CommutionType]
}

object CommutionType {
  def parse(token: String): CommutionType = {
    token match {
      case "M" => ByMotorcycle
      case "C" => ByCar
      case "B" => ByBicycle
    }
  }
}

case object ByMotorcycle extends CommutionType {
  override def stringify = "M"
}

case object ByCar extends CommutionType {
  override def stringify = "C"
}

case object ByBicycle extends CommutionType {
  override def stringify = "B"
}
