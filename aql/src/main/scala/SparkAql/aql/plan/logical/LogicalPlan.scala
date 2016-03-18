package SparkAql.aql.plan.logical

import SparkAql.aql.plan.TreeNode

abstract class LogicalPlan extends TreeNode[LogicalPlan] {
  private var _analyzed: Boolean = false

  def setAnalyzed(): Unit = {
    _analyzed = true
  }

  //Has this logicalPlan been analyzed?
  def analyzed: Boolean = _analyzed

}

/**
  * A logical plan node with no children.
  */
abstract class LeafNode extends LogicalPlan {
  override def children: Seq[LogicalPlan] = Nil
}

/**
  * A logical plan node with single child.
  */
abstract class UnaryNode extends LogicalPlan {
  def child: LogicalPlan

  override def children: Seq[LogicalPlan] = child :: Nil
}

/**
  * A logical plan node with a left and right child.
  */
abstract class BinaryNode extends LogicalPlan {
  def left: LogicalPlan

  def right: LogicalPlan

  override def children: Seq[LogicalPlan] = Seq(left, right)
}
