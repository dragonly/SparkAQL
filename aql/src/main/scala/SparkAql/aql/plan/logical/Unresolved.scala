package SparkAql.aql.plan.logical

case class UnresolvedDictView(DictName: String, ViewName: String) extends LeafNode{
  override def toString = "A new View named "+ViewName+" extracted from document with dictionary " + DictName
}
