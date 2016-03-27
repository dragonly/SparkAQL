package SparkAql.aql.analysis

import SparkAql.aql.AqlContext
import SparkAql.aql.plan.logical._
import SparkAql.aql.rule.{RuleExecutor,Rule}

/**
 * Unresolved LogicPlan => Resolved LogicPlan
 */
class Analyzer(aqlContext: AqlContext, maxIterations: Int = 100)
  extends RuleExecutor[LogicalPlan] with CheckAnalysis {

  val fixedPoint = FixedPoint(maxIterations)

  lazy val batches: Seq[Batch] = Seq(
    Batch("Resolution", fixedPoint,
      ResolveDictView
      //a lot to do here...
    )
  )

  object ResolveDictView extends Rule[LogicalPlan] {

    def apply(plan: LogicalPlan): LogicalPlan = plan resolveOperators {
      case u: UnresolvedDictView =>
        if(aqlContext.docSet){
          if(aqlContext.dictExists(u.DictName)){
            val res = ResolvedDictView(aqlContext,u.DictName)
            aqlContext.registerView(u.ViewName,res)
            res
          }else{
            NoDictPlan("can't find dictionary $").failAnalysis()
          }
        }else{
          NoDocPlan("No Document has been set").failAnalysis()
        }

    }
  }


}
