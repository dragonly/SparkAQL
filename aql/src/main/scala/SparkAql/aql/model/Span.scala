package SparkAql.aql.model

/**
 * Span:<begin,end>
 */
class Span(begin: Int, end: Int) {
  override def toString = "begin at: " + begin +"; end at: " + end
}
