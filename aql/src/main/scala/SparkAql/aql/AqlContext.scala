package SparkAql.aql

import SparkAql.aql.catalog.Dictionary
import SparkAql.aql.parser.AqlParser
import SparkAql.aql.plan.logical.LogicalPlan
import org.apache.spark.SparkContext

/**
  * AQL context: register Dic, execute AQL queries
  */
class AqlContext(@transient val sparkContext: SparkContext) {

  @transient
  protected lazy val dictionaries = new Dictionary

  def registerDictionary(DictName: String, tokens: Seq[String]): Unit = {
    dictionaries.registerDict(DictName, tokens)
  }

  def lookforDict(DictName: String): Seq[String] = {
    dictionaries.lookforDict(DictName)
  }

  def aql(input: String): LogicalPlan = {
    AqlParser.parse(input)
  }

}