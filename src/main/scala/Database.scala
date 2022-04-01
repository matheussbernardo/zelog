case class Database(assertions: LazyList[Pattern], indexedAssertions: Map[String, LazyList[Pattern]]) {
  def getAssertions(pattern: Pattern): LazyList[Pattern] =
    pattern match {
      case Pattern.Pair(Pattern.Const(id), second) => getIndexedAssertions(id)
      case Pattern.Pair(first, second) => assertions
      case Pattern.Const(id) => getIndexedAssertions(id)
      case Pattern.Variable(id) => assertions
    }

  private def getIndexedAssertions(key: String): LazyList[Pattern] =
    indexedAssertions.getOrElse(key, LazyList.empty)

  def addAssertion(pattern: Pattern): Database =
    pattern match {
      case Pattern.Const(id) =>
        this.copy(
          assertions = addToAssertions(pattern),
          indexedAssertions = addToIndexedAssertion(pattern, id)
        )
      case Pattern.Variable(id) =>
        this.copy(
          assertions = addToAssertions(pattern)
        )
      case Pattern.Pair(Pattern.Const(id), second) =>
        this.copy(
          assertions = addToAssertions(pattern),
          indexedAssertions = addToIndexedAssertion(pattern, id)
        )
      case Pattern.Pair(first, second) =>
        this.copy(
          assertions = addToAssertions(pattern)
        )

    }

  private def addToAssertions(pattern: Pattern) = {
    pattern #:: this.assertions
  }

  private def addToIndexedAssertion(pattern: Pattern, id: String) = {
    if (this.indexedAssertions.contains(id)) {
      this.indexedAssertions + ((id, pattern #:: this.indexedAssertions(id)))
    } else {
      this.indexedAssertions.+((id, LazyList(pattern)))
    }
  }
}