package SparkAql.aql.analysis

import SparkAql.aql.exception.AnalysisException
import SparkAql.aql.plan.logical.{LogicalPlan, UnresolvedDictView}

/**
 * Throws user facing errors when passed invalid queries that fail to analyze.
 */
trait CheckAnalysis {

  val extendedCheckRules: Seq[LogicalPlan => Unit] = Nil

  protected def failAnalysis(msg: String): Nothing = {
    throw new AnalysisException(msg)
  }

  def checkAnalysis(plan: LogicalPlan): Unit = {
    // We transform up and order the rules so as to catch the first possible failure instead
    // of the result of cascading resolution failures.
    plan.foreachUp {
      case p if p.analyzed => // Skip already analyzed sub-plans
      case u: UnresolvedDictView => {
        failAnalysis(s"Dictionary not found: ${u.DictName}")
      }

      //a lot to do here...

      case _ => // Analysis successful!
    }

    extendedCheckRules.foreach(_(plan))
    plan.foreach(_.setAnalyzed())
  }
}
