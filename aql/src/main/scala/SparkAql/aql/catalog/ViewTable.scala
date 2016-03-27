package SparkAql.aql.catalog

import java.util.concurrent.ConcurrentHashMap
import SparkAql.aql.plan.logical.LogicalPlan

/**
 * a catlog which stores <ViewName, Analyzed LogicalPlan>
 */
class ViewTable {
  private[this] val views = new ConcurrentHashMap[String, LogicalPlan]

  def registerView(name: String, logical: LogicalPlan): Unit = {
    views.put(name, logical)
  }

  def unregisterAllViews(): Unit = {
    views.clear()
  }

  def viewExists(name: String): Boolean = {
    views.containsKey(name)
  }

  def lookforView(name: String): LogicalPlan = {
    views.get(name)
  }

}
