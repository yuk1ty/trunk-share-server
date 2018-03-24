package domain

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