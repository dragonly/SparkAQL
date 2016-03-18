package SparkAql.aql.plan.logical

case class TestStr(input: String) extends LeafNode {
  override def toString = "Create a new view named "+ input
}