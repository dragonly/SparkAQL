package SparkAql.aql.model

import SparkAql.aql.AqlContext
import SparkAql.aql.execution.QueryExecution
import SparkAql.aql.plan.logical.LogicalPlan

/**
 * abstract Relation of tuple
 */
class View(val aqlContext: AqlContext, val queryExecution: QueryExecution) {

  /**
   * A constructor that automatically analyzes the logical plan. and register view iff analyzed
   */
  def this(aqlContext: AqlContext, logicalPlan: LogicalPlan) = {
    this(aqlContext, {
      val qe = aqlContext.executePlan(logicalPlan)
      qe.assertAnalyzed()
      qe
    })
  }

  def printPlan(): Unit ={
    println(queryExecution.analyzed)
  }

}

object View {
  def apply(aqlContext: AqlContext, logicalPlan: LogicalPlan): View = {
    new View(aqlContext, logicalPlan)
  }
}
