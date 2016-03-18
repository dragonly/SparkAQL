package SparkAql.aql.parser

import SparkAql.aql.plan.logical.{TestStr,LogicalPlan}

object AqlParser extends AbstractSparkAQLParser {

  //keywords
  protected val CREATE = Keyword("CREATE")
  protected val VIEW = Keyword("VIEW")


  protected lazy val start: Parser[LogicalPlan] = view1

  protected lazy val view1: Parser[LogicalPlan] =
    "create" ~> "view" ~> ident ^^ {
      case str => TestStr(str)
    }


}
