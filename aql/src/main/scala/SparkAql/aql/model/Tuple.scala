package SparkAql.aql.model

/**
 * seq of spans
 */
class Tuple(val spans: Seq[Span]) extends Serializable{

  def addSpan(newSpan: Span): Seq[Span] ={
    val res = spans:+newSpan
    res
  }

  def combineWithTuple(tupleA: Seq[Span]): Seq[Span] ={
    val res = spans++tupleA
    res
  }
}
