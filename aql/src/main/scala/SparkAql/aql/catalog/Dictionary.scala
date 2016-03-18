package SparkAql.aql.catalog

import java.util.concurrent.ConcurrentHashMap

/**
  * a catalog which stores registered dictionaries
  */
class Dictionary {
  private[this] val dictionaries = new ConcurrentHashMap[String, Seq[String]]

  def registerDict(name: String, tokens: Seq[String]): Unit = {
    dictionaries.put(name, tokens)
  }

  def unregisterAllDicts(): Unit = {
    dictionaries.clear()
  }

  def dictExists(name: String): Boolean = {
    dictionaries.contains(name)
  }

  def lookforDict(name: String): Seq[String] = {
    dictionaries.get(name)
  }

}
