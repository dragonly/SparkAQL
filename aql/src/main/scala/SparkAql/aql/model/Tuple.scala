package SparkAql.aql.model

/**
 * seq of spans
 */
class Tuple(spans: Seq[Span]) {

  def addSpan(newSpan: Span): Seq[Span] ={
    val res = spans:+newSpan
    res
  }

  def combineWithTuple(tupleA: Seq[Span]): Seq[Span] ={
    val res = spans++tupleA
    res
  }
}
