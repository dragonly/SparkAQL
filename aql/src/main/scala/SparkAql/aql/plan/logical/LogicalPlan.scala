package SparkAql.aql.plan.logical

import SparkAql.aql.plan.TreeNode

abstract class LogicalPlan extends TreeNode[LogicalPlan] {
  private var _analyzed: Boolean = false

  def setAnalyzed(): Unit = {
    _analyzed = true
  }

  //Has this logicalPlan been analyzed?
  def analyzed: Boolean = _analyzed

  lazy val schema: Seq[String] = output

  /**
   * Returns a copy of this node where `rule` has been recursively applied first to all of its
   * children and then itself (post-order). When `rule` does not apply to a given node, it is left
   * unchanged.  This function is similar to `transformUp`, but skips sub-trees that have already
   * been marked as analyzed.
   *
   * @param rule the function use to transform this nodes children
   */
  def resolveOperators(rule: PartialFunction[LogicalPlan, LogicalPlan]): LogicalPlan = {
    if (!analyzed) {
      val afterRuleOnChildren = transformChildren(rule, (t, r) => t.resolveOperators(r))
      if (this fastEquals afterRuleOnChildren) {
        rule.applyOrElse(this, identity[LogicalPlan])
      } else {
        rule.applyOrElse(afterRuleOnChildren, identity[LogicalPlan])
      }
    } else {
      this
    }
  }

}

/**
  * A logical plan node with no children.
  */
abstract class LeafLogicalNode extends LogicalPlan {
  override def children: Seq[LogicalPlan] = Nil
}

/**
  * A logical plan node with single child.
  */
abstract class UnaryLogicalNode extends LogicalPlan {
  def child: LogicalPlan

  override def children: Seq[LogicalPlan] = child :: Nil
}

/**
  * A logical plan node with a left and right child.
  */
abstract class BinaryLogicalNode extends LogicalPlan {
  def left: LogicalPlan

  def right: LogicalPlan

  override def children: Seq[LogicalPlan] = left :: right :: Nil
}
