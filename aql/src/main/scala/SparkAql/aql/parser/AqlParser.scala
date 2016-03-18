package SparkAql.aql.parser

import SparkAql.aql.plan.logical.{UnresolvedDictView, LogicalPlan}

object AqlParser extends AbstractSparkAQLParser {

  //keywords
  protected val CREATE = Keyword("CREATE")
  protected val VIEW = Keyword("VIEW")
  protected val AS = Keyword("AS")
  protected val EXTRACT = Keyword("EXTRACT")
  protected val DICTIONARY = Keyword("DICTIONARY")
  protected val FROM = Keyword("FROM")
  protected val DOCUMENT = Keyword("DOCUMENT")

  protected lazy val start: Parser[LogicalPlan] = dictView

  protected lazy val dictView: Parser[LogicalPlan] =
    EXTRACT ~> DICTIONARY ~> ident ~ (AS ~> ident) <~ FROM <~ DOCUMENT^^ {
      case dictName ~ viewName => UnresolvedDictView(dictName,viewName)
    }

}
