package domain

case class Driver(id: Long, startPoint: String, endPoint: String, rating: Int)
    extends TrunkShareResponse
