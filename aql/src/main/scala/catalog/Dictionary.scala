package SparkAql.aql.catalog

import java.util.concurrent.ConcurrentHashMap

/**
 * a catalog which stores registered dictionaries
 */
class Dictionary {
  private[this] val dictionaries = new ConcurrentHashMap[String,Seq[String]]

  def registerDic(name: String, tokens: Seq[String]): Unit ={
    dictionaries.put(name, tokens)
  }

  def unregisterAllDics(): Unit ={
    dictionaries.clear()
  }

  def dicExists(name: String): Boolean ={
    dictionaries.contains(name)
  }

  def lookforDic(name: String): Seq[String] ={
    dictionaries.get(name)
  }

}
