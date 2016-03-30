package SparkAql.aql.model

/**
 * Span:<begin,end>
 */
class Span(val begin: Int, val end: Int) extends Serializable{
  override def toString = "begin at: " + begin +"(inclusive); end at: " + end + "(exclusive)."
}
