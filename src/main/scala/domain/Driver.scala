package domain

import infra.MySqlEnum

// TODO circe のパースがおかしいので直す
case class Driver(id: Long,
                  name: String,
                  whereFrom: String,
                  whereTo: String,
                  startAt: String,
                  endAt: String,
                  rating: Int,
                  commutionType: CommutionType,
                  frequency: Frequency)
    extends TrunkShareResponse


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
