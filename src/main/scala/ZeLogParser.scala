import scala.util.parsing.combinator.*

class ZeLogParser extends RegexParsers {
  private def constParser =
    """\w+""".r ^^ {
      Pattern.Const.apply
    }

  private def variableParser =
    elem('?') ~> """\w+""".r ^^ {
      Pattern.Variable.apply
    }

  private def constOrVariableParser =
    constParser | variableParser

  private def pairParser =
    val firstPattern = constOrVariableParser <~ elem('(')
    val listOfPattern = rep1sep(constOrVariableParser, ",") <~ elem(')')
    firstPattern ~ listOfPattern ^^ { result => toPair(result._1 :: result._2) }

  private def patternParser =
    pairParser | constOrVariableParser
  
  private def toPair(list: List[Pattern]): Pattern =
    list match {
      case head :: second :: Nil => Pattern.Pair(head, second)
      case head :: next => Pattern.Pair(head, toPair(next))
      case _ => throw Exception("Failure to parse list")
    }


  def run(text: String): Pattern =
    val result = parse(phrase(patternParser), text)
    result match {
      case Success(matched, _) => matched
      case Failure(msg, _) => throw Exception("FAILURE: " + msg)
      case Error(msg, _) => throw Exception("ERROR: " + msg)
    }
}

/*

// Parsing

job(Matheus, Programador) -> Pair(Const("job"), Const("Matheus"), Const("Programador"))
job -> Const("Job")
job(?x, Programador) -> Pair(Const("job"), Var("x"), Const("Programador"))


# is_programador(?x) = job(?x, Programador)
    ->  Rule(
          Pair(Const("is_programador"), Var("x")),  // conclusion : Pattern
          Pair(Const("job"), Var("x"), Const("Programador")) // body : Pattern
        )

# job(Matheus, Programador) -> Pair(Const("job"), Const("Matheus"), Const("Programador"))


Database [
    Asserions[
      pattern,
      pattern,
      pattern
    ]
    Rules [
      (conclusion, body)
      (conclusion, body)
      (conclusion, body)
      (conclusion, body)
    ]
]

*/