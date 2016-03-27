package SparkAql.aql.plan.logical

import SparkAql.aql.exception.AnalysisException

/**
 * LogicalPlan used to throw errors
 */

case class NoDocPlan(msg: String) extends LeafNode{
  def failAnalysis(): Nothing = {
    throw new AnalysisException(msg)
  }
}

case class NoDictPlan(msg: String) extends LeafNode{
  def failAnalysis(): Nothing = {
    throw new AnalysisException(msg)
  }
}