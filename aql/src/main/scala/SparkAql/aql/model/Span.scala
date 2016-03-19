package SparkAql.aql.model

/**
 * Span:<begin,end>
 */
class Span(val begin: Int, val end: Int) {
  override def toString = "begin at: " + begin +"; end at: " + end
}
