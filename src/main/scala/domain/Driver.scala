package domain

import infra.MySqlEnum

// TODO circe のパースがおかしいので直す
case class Driver(id: Long,
                  whereFrom: String,
                  whereTo: String,
                  startAt: String,
                  endAt: String,
                  rating: Int,
                  commutionType: CommutionType,
                  frequency: Frequency)
    extends TrunkShareResponse

sealed trait CommutionType extends MySqlEnum

case object ByCar extends CommutionType {
  override def stringify: String = "C"
}

case object ByMotorcycle extends CommutionType {
  override def stringify: String = "M"
}

case object ByBicycle extends CommutionType {
  override def stringify: String = "B"
}

sealed trait Frequency extends MySqlEnum

case object Weekdays extends Frequency {
  override def stringify: String = "W"
}

case object Holidays extends Frequency {
  override def stringify: String = "H"
}

case object Others extends Frequency {
  override def stringify: String = "O"
}
