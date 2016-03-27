package SparkAql.aql.rule

import SparkAql.aql.plan.TreeNode

/**
 * abstract rule that used in analysis and optimization
 */
abstract class Rule[TreeType <: TreeNode[_]] {

  /** Name for this rule, automatically inferred based on class name. */
  val ruleName: String = {
    val className = getClass.getName
    if (className endsWith "$") className.dropRight(1) else className
  }

  def apply(plan: TreeType): TreeType

}
