enum Pattern:
  case Const(val id: String)
  case Variable(val id: String)
  case Pair(val first: Pattern, val second: Pattern)