import scala.io.StdIn.readLine

class QueryDriverLoop(private val parser: ZeLogParser, private var database: Database) {
  def start(): Unit =
    while (true) {
      val input = readLine("$ZeLog>")
      if (input.startsWith("!!")) {
        addAssertionToDatabase(input)
      } else {
        val query = parser.run(input)
        val result = evalSimpleQuery(query, emptyFrame).filter(_.isRight)
        result.foreach(frame => println(frame))
      }
    }

  private def evalSimpleQuery(query: Pattern, frame: Frame): LazyList[Frame] =
    val assertions = database.getAssertions(query)
    assertions.map(datum => patternMatch(query, datum, frame))

  private def addAssertionToDatabase(input: String): Unit = {
    val assertionAsStr = input.drop(2)
    val assertion = parser.run(assertionAsStr)
    database = database.addAssertion(assertion)
  }
}
