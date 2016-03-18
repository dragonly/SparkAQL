package SparkAql.aql.plan

abstract class TreeNode[BaseType <: TreeNode[BaseType]] {
  self:BaseType =>
  def children: Seq[BaseType]
}
