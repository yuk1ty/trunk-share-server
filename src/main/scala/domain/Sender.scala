package domain

case class Sender(id: Long,
                  name: String,
                  whereFrom: String,
                  whereTo: String,
                  startAt: String,
                  endAt: String,
                  luggageType: LuggageType,
                  fragile: Boolean)
