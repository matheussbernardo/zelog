class ZeLogParserTest extends munit.FunSuite {

  test("zelog parser simple const") {
    // prepare
    val text = "job"
    val zeLogParser = new ZeLogParser

    // act
    val result = zeLogParser.run(text)

    // assert
    assertEquals(result, Pattern.Const("job"))
  }

  test("zelog parser simple variable") {
    // prepare
    val text = "?x123"
    val zeLogParser = new ZeLogParser

    // act
    val result = zeLogParser.run(text)

    // assert
    assertEquals(result, Pattern.Variable("x123"))
  }

  test("zelog parser simple pair") {
    // prepare
    val text = "test(1,2)"
    val expectedPattern = Pattern.Pair(
      Pattern.Const("test"),
      Pattern.Pair(
        Pattern.Const("1"),
        Pattern.Const("2")
      )
    )
    val zeLogParser = new ZeLogParser

    // act
    val result = zeLogParser.run(text)

    // assert
    assertEquals(result, expectedPattern)
  }

  test("zelog parser  pair with variable") {
    // prepare
    val text = "test(1,2,?x)"
    val expectedPattern = Pattern.Pair(
      Pattern.Const("test"),
      Pattern.Pair(
        Pattern.Const("1"),
        Pattern.Pair(
          Pattern.Const("2"),
          Pattern.Variable("x")
        )
      )
    )
    val zeLogParser = new ZeLogParser

    // act
    val result = zeLogParser.run(text)

    // assert
    assertEquals(result, expectedPattern)
  }
}
