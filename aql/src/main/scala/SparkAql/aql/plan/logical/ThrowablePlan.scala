package SparkAql.aql.plan.logical

import SparkAql.aql.exception.AnalysisException

/**
 * logic plan used to throw exceptions
 */
case class ThrowablePlan(msg: String) extends LeafLogicalNode{
  override def output: Seq[String] = Nil

  def failAnalysis(): Nothing = {
    throw new AnalysisException(msg)
  }
}
