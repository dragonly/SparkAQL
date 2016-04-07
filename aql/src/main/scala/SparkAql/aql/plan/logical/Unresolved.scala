package SparkAql.aql.plan.logical

case class UnresolvedDictView(DictName: String, ViewName: String, output: Seq[String]) extends LeafLogicalNode {
  override def toString = "An unresolved View named " + ViewName + " extracted from document with dictionary " + DictName
}

case class UnresolvedRegexView(regex: String, ViewName: String, output: Seq[String]) extends LeafLogicalNode {
  override def toString = "An unresolved View named " + ViewName + " extracted from document with dictionary " + regex
}
