package usecase

trait SenderUsecase {}

object SenderUsecase extends SenderUsecase

trait UsesSenderUsecase {
  val senderUsecase: SenderUsecase
}

trait MixInSenderUsecase {
  val senderUsecase: SenderUsecase = SenderUsecase
}
