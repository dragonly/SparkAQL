package SparkAql.aql.exception

import SparkAql.aql.plan.TreeNode
/**
 * Created by simon on 16-3-27.
 */
class TreeNodeException[TreeType <: TreeNode[_]](tree: TreeType, msg: String, cause: Throwable)
  extends Exception(msg, cause) {

  // Yes, this is the same as a default parameter, but... those don't seem to work with SBT
  // external project dependencies for some reason.
  def this(tree: TreeType, msg: String) = this(tree, msg, null)

  override def getMessage: String = {
    val treeString = tree.toString
    s"${super.getMessage}, tree:${if (treeString contains "\n") "\n" else " "}$tree"
  }
}
