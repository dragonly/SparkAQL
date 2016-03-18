import SparkAql.aql.catalog.Dictionary
import org.apache.spark.SparkContext

/**
 * AQL context: register Dic, execute AQL queries
 */
class AqlContext(sc: SparkContext) {

  @transient
  protected lazy val dictionaries = new Dictionary

  def registerDictionary(DicName: String, tokens: Seq[String]): Unit ={
    dictionaries.registerDic(DicName, tokens)
  }

  def lookforDic(DicName: String): Seq[String] ={
    dictionaries.lookforDic(DicName)
  }

}
