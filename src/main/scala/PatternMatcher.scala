
def patternMatch(query: Pattern, datum: Pattern, frame: Frame): Frame =
  (query, datum) match {
    case (Pattern.Const(id), Pattern.Const(id2)) if id == id2 => frame
    case (Pattern.Variable(id), dat) => extendIfConsistent(query, dat, frame)
    case (Pattern.Pair(firstQuery, secondQuery), Pattern.Pair(firstDatum, secondDatum)) =>
      val frameOfFirst = patternMatch(firstQuery, firstDatum, frame)
      patternMatch(secondQuery, secondDatum, frameOfFirst)
    case _ => Left("DOES NOT MATCH")
  }


def extendIfConsistent(variable: Pattern, datum: Pattern, frame: Frame): Frame =
  frame match {
    case Left(value) => Left(value)
    case Right(bindings) =>
      variable match {
        case Pattern.Variable(id) => if (bindings.contains(id)) {
          patternMatch(bindings(id), datum, frame)
        } else {
          addBinding((id, datum), frame)
        }
        case Pattern.Const(id) => ???
        case Pattern.Pair(first, second) => ???
      }
  }

