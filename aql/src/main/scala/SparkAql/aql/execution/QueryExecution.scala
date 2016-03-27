package SparkAql.aql.execution

import SparkAql.aql.AqlContext
import SparkAql.aql.plan.logical.LogicalPlan

/**
 * The primary workflow for executing relational queries using Spark.
 */
class QueryExecution(val aqlContext: AqlContext, val logical: LogicalPlan) {

  def assertAnalyzed(): Unit = aqlContext.analyzer.checkAnalysis(analyzed)
  lazy val analyzed: LogicalPlan = aqlContext.analyzer.execute(logical)

}
