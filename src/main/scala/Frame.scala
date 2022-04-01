type Frame = Either[String, Map[String, Pattern]]
def emptyFrame: Frame = Right(Map())
def failedFrame: Frame = Left("Failed matching")
def addBinding(binding: (String, Pattern), frame: Frame): Frame =
  frame.map(_.+(binding))
