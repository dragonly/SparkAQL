package SparkAql.aql.exception

/**
 * Thrown when a query fails to analyze, usually because the query itself is invalid.
 */
class AnalysisException(message: String) extends Exception{

  override def getMessage: String = {
    message
  }
}

class RegexException extends Exception
