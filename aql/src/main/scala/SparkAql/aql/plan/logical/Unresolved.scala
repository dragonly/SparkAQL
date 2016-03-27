package SparkAql.aql.plan.logical

case class UnresolvedDictView(DictName: String, ViewName: String) extends LeafNode {
  override def toString = "An unresolved View named " + ViewName + " extracted from document with dictionary " + DictName
}
