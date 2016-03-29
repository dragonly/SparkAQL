package SparkAql.aql.plan.physical

import java.util.concurrent.atomic.AtomicBoolean

import SparkAql.aql.AqlContext
import SparkAql.aql.model.Tuple
import SparkAql.aql.plan.TreeNode
import org.apache.spark.rdd.RDD

abstract class SparkPlan extends TreeNode[SparkPlan] {

  /**
   * A handle to the AqlContext that was used to create this plan.   Since many operators need
   * access to the aqlContext for RDD operations or configuration this field is automatically
   * populated by the query planning infrastructure.
   */
  @transient
  protected[aql] final val aqlContext = AqlContext.getActive().getOrElse(null)

  protected def sparkContext = aqlContext.sparkContext

  /**
   * Whether the "prepare" method is called.
   */
  private val prepareCalled = new AtomicBoolean(false)

  /**
   * Returns the result of this query as an RDD[InternalRow] by delegating to doExecute
   * after adding query plan information to created RDDs for visualization.
   * Concrete implementations of SparkPlan should override doExecute instead.
   */
  final def execute(): RDD[Tuple] = {
    prepare()
    doExecute()
  }

  /**
   * Prepare a SparkPlan for execution. It's idempotent.
   */
  final def prepare(): Unit = {
    if (prepareCalled.compareAndSet(false, true)) {
      doPrepare()
      children.foreach(_.prepare())
    }
  }

  /**
   * Overridden by concrete implementations of SparkPlan. It is guaranteed to run before any
   * `execute` of SparkPlan. This is helpful if we want to set up some state before executing the
   * query, e.g., `BroadcastHashJoin` uses it to broadcast asynchronously.
   *
   * Note: the prepare method has already walked down the tree, so the implementation doesn't need
   * to call children's prepare methods.
   */
  protected def doPrepare(): Unit = {}

  /**
   * Overridden by concrete implementations of SparkPlan.
   * Produces the result of the query as an RDD[InternalRow]
   */
  protected def doExecute(): RDD[Tuple]


}


/**
 * A Spark plan node with no children.
 */
abstract class LeafSparkNode extends SparkPlan {
  override def children: Seq[SparkPlan] = Nil
}

/**
 * A Spark plan node with single child.
 */
abstract class UnarySparkNode extends SparkPlan {
  def child: SparkPlan

  override def children: Seq[SparkPlan] = child :: Nil
}

/**
 * A Spark plan node with a left and right child.
 */
abstract class BinarySparkNode extends SparkPlan {
  def left: SparkPlan

  def right: SparkPlan

  override def children: Seq[SparkPlan] = left :: right :: Nil
}
