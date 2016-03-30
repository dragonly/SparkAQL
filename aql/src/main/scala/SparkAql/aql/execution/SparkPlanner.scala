package SparkAql.aql.execution

import SparkAql.aql.AqlContext
import SparkAql.aql.plan.logical.{LogicalPlan, ResolvedDictView}
import SparkAql.aql.plan.physical.{PhysicalDictView, SparkPlan}
import org.apache.spark.SparkContext

/**
 * implementation of QueryPlanner
 */
class SparkPlanner(val aqlContext: AqlContext) extends QueryPlanner[SparkPlan]{
  val sparkContext: SparkContext = aqlContext.sparkContext

  def strategies: Seq[SparkStrategy[SparkPlan]] = Seq(
    TestStrategy
    )



  //implementation of strategies
  object TestStrategy extends SparkStrategy[SparkPlan]{

    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case ResolvedDictView(aqlContext, dictName) => PhysicalDictView(dictName)::Nil
    }
  }

}
