package SparkAql.aql.analysis

import java.util.regex.Pattern

import SparkAql.aql.AqlContext
import SparkAql.aql.exception.RegexException
import SparkAql.aql.plan.logical._
import SparkAql.aql.rule.{Rule, RuleExecutor}

/**
 * Unresolved LogicPlan => Resolved LogicPlan
 */
class Analyzer(aqlContext: AqlContext, maxIterations: Int = 100)
  extends RuleExecutor[LogicalPlan] with CheckAnalysis {

  val fixedPoint = FixedPoint(maxIterations)

  lazy val batches: Seq[Batch] = Seq(
    Batch("Resolution", fixedPoint,
      ResolveDictView::
      ResolveRegexView::
        Nil: _*
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
            ThrowablePlan(s"Can't find dictionary ${u.DictName}").failAnalysis()
          }
        }else{
          ThrowablePlan("No Document has been set").failAnalysis()
        }

    }
  }

  object ResolveRegexView extends Rule[LogicalPlan] {

    def apply(plan: LogicalPlan): LogicalPlan = plan resolveOperators {
      case u: UnresolvedRegexView =>
        if(aqlContext.docSet){
          try {
            Pattern.compile(u.regex)
            val res = ResolvedRegexView(aqlContext,u.regex)
            aqlContext.registerView(u.ViewName,res)
            res
          }catch {
            case _: RegexException => ThrowablePlan(s"Regex ${u.regex} is wrong").failAnalysis()
          }
        }else{
          ThrowablePlan("No Document has been set").failAnalysis()
        }
    }
  }

}
