package SparkAql.aql.plan.logical

import SparkAql.aql.AqlContext


case class ResolvedDictView(aqlContext: AqlContext, DictName: String) extends LeafLogicalNode {
  override def toString = "A resolved View extracted from document with dictionary " + DictName
}