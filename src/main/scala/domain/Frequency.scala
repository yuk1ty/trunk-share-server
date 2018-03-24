package domain

import infra.MySqlEnum

sealed trait Frequency extends MySqlEnum {

}

object Frequency {
  def parse(token: String): Frequency = {
    token match {
      case "W" => Weekdays
      case "H" => Holidays
      case "O" => Others
    }
  }
}

case object Weekdays extends Frequency {
  override def stringify: String = "W"
}

case object Holidays extends Frequency {
  override def stringify: String = "H"
}

case object Others extends Frequency {
  override def stringify: String = "O"
}

