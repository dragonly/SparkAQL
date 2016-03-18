package SparkAql.aql.plan

/**
 * Created by simon on 16-3-17.
 */
abstract class TreeNode[BaseType <: TreeNode[BaseType]] {
  self:BaseType =>
  def children: Seq[BaseType]
}
