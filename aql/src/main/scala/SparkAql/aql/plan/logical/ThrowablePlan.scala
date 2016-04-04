package SparkAql.aql.plan.logical

import SparkAql.aql.exception.AnalysisException

/**
 * logic plan used to throw exceptions
 */
case class ThrowablePlan(msg: String) extends LeafLogicalNode{

  def failAnalysis(): Nothing = {
    throw new AnalysisException(msg)
  }
}
