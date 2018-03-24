package domain

import infra.MySqlEnum

sealed trait LuggageType extends MySqlEnum

object LuggageType {
  def parse(token: String): LuggageType = {
    token match {
      case "F"  => Food
      case "L"  => Liquid
      case "T"  => Tableware
      case "FU" => Furniture
      case "C"  => Cloth
    }
  }
}

case object Food extends LuggageType {
  override def stringify = "F"
}

case object Liquid extends LuggageType {
  override def stringify: String = "L"
}

case object Tableware extends LuggageType {
  override def stringify: String = "T"
}

case object Furniture extends LuggageType {
  override def stringify: String = "FU"
}

case object Cloth extends LuggageType {
  override def stringify: String = "C"
}

case object Other extends LuggageType {
  override def stringify: String = "O"
}
