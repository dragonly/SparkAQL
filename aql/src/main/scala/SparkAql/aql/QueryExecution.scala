package SparkAql.aql

import SparkAql.aql.model.Tuple
import SparkAql.aql.plan.logical.LogicalPlan
import SparkAql.aql.plan.physical.SparkPlan
import org.apache.spark.rdd.RDD

/**
 * The primary workflow for executing relational queries using Spark.
 */
class QueryExecution(val aqlContext: AqlContext, val logical: LogicalPlan) {

  def assertAnalyzed(): Unit = aqlContext.analyzer.checkAnalysis(analyzed)
  lazy val analyzed: LogicalPlan = aqlContext.analyzer.execute(logical)

  lazy val sparkPlan: SparkPlan = {
    AqlContext.setActive(aqlContext)
    aqlContext.planner.plan(analyzed).next()
  }

  lazy val toRdd: RDD[Tuple] = sparkPlan.execute()


}
