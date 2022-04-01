class PatternMatcherTest extends munit.FunSuite {

  test("matches job job [] -> []") {
    // prepare
    val query = Pattern.Const("job")
    val datum = Pattern.Const("job")
    val frame = emptyFrame

    // act
    val resultFrame = patternMatch(query, datum, frame)

    // assert
    val expectedFrame = frame
    assertEquals(resultFrame, expectedFrame)
  }

  test("matches ?x job [] -> [x -> job]") {
    // prepare
    val query = Pattern.Variable("x")
    val datum = Pattern.Const("job")
    val frame = emptyFrame

    // act
    val resultFrame = patternMatch(query, datum, frame)

    // assert
    val expectedFrame = Right(Map("x" -> Pattern.Const("job")))
    assertEquals(resultFrame, expectedFrame)
  }

  test("matches (job 2) (job 2) [] -> []") {
    // prepare
    val query = Pattern.Pair(Pattern.Const("job"), Pattern.Const("2"))
    val datum = Pattern.Pair(Pattern.Const("job"), Pattern.Const("2"))
    val frame = emptyFrame

    // act
    val resultFrame = patternMatch(query, datum, frame)

    // assert
    val expectedFrame = frame
    assertEquals(resultFrame, expectedFrame)
  }

  test("matches (job 2 ?x) (job 2 A) [] -> [x -> A]") {
    // prepare
    val query = Pattern.Pair(Pattern.Const("job"), Pattern.Pair(Pattern.Const("2"), Pattern.Variable("x")))
    val datum = Pattern.Pair(Pattern.Const("job"), Pattern.Pair(Pattern.Const("2"), Pattern.Const("A")))
    val frame = emptyFrame

    // act
    val resultFrame = patternMatch(query, datum, frame)

    // assert
    val expectedFrame = frame
    assertEquals(resultFrame, Right(Map("x" -> Pattern.Const("A"))))
  }

  test("matches (job 2 ?x) (job 5 A) [] -> Failure") {
    // prepare
    val query = Pattern.Pair(Pattern.Const("job"), Pattern.Pair(Pattern.Const("2"), Pattern.Variable("x")))
    val datum = Pattern.Pair(Pattern.Const("job"), Pattern.Pair(Pattern.Const("5"), Pattern.Const("A")))
    val frame = emptyFrame

    // act
    val resultFrame = patternMatch(query, datum, frame)

    // assert
    assertEquals(resultFrame, Left("DOES NOT MATCH"))
  }

  test("matches (job 2 ?x) (job 2 B) [x -> A] -> Failure") {
    // prepare
    val query = Pattern.Pair(Pattern.Const("job"), Pattern.Pair(Pattern.Const("2"), Pattern.Variable("x")))
    val datum = Pattern.Pair(Pattern.Const("job"), Pattern.Pair(Pattern.Const("2"), Pattern.Const("B")))
    val frame = Right(Map[String, Pattern]("x" -> Pattern.Const("A")))

    // act
    val resultFrame = patternMatch(query, datum, frame)

    // assert
    assertEquals(resultFrame, Left("DOES NOT MATCH"))
  }
}

