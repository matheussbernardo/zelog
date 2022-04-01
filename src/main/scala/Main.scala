import scala.io.StdIn.readLine

@main def main(): Unit =
  val parser = ZeLogParser()
  val database = Database(LazyList.empty, Map.empty)
  val driverLoop = QueryDriverLoop(parser, database)
  driverLoop.start()




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