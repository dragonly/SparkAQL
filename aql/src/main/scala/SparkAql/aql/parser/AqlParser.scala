package SparkAql.aql.parser

import SparkAql.aql.plan.logical.{UnresolvedRegexView, LogicalPlan, UnresolvedDictView}

object AqlParser extends AbstractSparkAQLParser {

  //keywords
  protected val CREATE = Keyword("CREATE")
  protected val VIEW = Keyword("VIEW")
  protected val AS = Keyword("AS")
  protected val EXTRACT = Keyword("EXTRACT")
  protected val DICTIONARY = Keyword("DICTIONARY")
  protected val REGEX = Keyword("REGEX")
  protected val FROM = Keyword("FROM")
  protected val DOCUMENT = Keyword("DOCUMENT")

  protected lazy val start: Parser[LogicalPlan] = dictView|regexView

  protected lazy val dictView: Parser[LogicalPlan] =
    CREATE ~> VIEW ~> ident ~ (AS ~> EXTRACT ~> DICTIONARY ~> ident) ~ (AS ~> ident) <~ FROM <~ DOCUMENT ^^ {
      case viewName ~ dictName ~ output => UnresolvedDictView(dictName, viewName, Seq(output))
    }

  protected lazy val regexView: Parser[LogicalPlan] =
    CREATE ~> VIEW ~> ident ~ (AS ~> EXTRACT ~> REGEX ~> stringLit) ~ (AS ~> ident) <~ FROM <~ DOCUMENT ^^ {
      case viewName ~ regex ~ output => UnresolvedRegexView(regex, viewName, Seq(output))
    }

}
