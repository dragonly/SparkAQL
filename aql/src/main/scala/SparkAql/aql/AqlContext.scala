package SparkAql.aql

import java.util.concurrent.atomic.AtomicReference

import SparkAql.aql.analysis.Analyzer
import SparkAql.aql.catalog.{Dictionary, ViewTable}
import SparkAql.aql.execution.SparkPlanner
import SparkAql.aql.model.View
import SparkAql.aql.parser.AqlParser
import SparkAql.aql.plan.logical.LogicalPlan
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * AQL context: register Dic, execute AQL queries
  */

class AqlContext(@transient val sparkContext: SparkContext) extends Serializable{
  // doc Text
  private var docText: String = ""
  private var _docSet: Boolean = false

  def setDocument(doc: RDD[String]): Unit = {
    docText = {
      val res = new StringBuilder
      for(elem <- doc.collect()){
        res.append(elem+" ")
      }
      res.toString
    }
    _docSet = true
  }

  def docSet: Boolean = _docSet

  def getDocument: String = {
    docText
  }

  //dictionaries
  @transient
  protected lazy val dictionaries = new Dictionary

  def registerDictionary(dictName: String, tokens: Seq[String]): Unit = {
    dictionaries.registerDict(dictName, tokens)
  }

  def lookforDict(dictName: String): Seq[String] = {
    dictionaries.lookforDict(dictName)
  }

  def dictExists(dictName: String): Boolean = {
    dictionaries.dictExists(dictName)
  }

  @transient
  protected[aql] lazy val analyzer: Analyzer = new Analyzer(this)

  @transient
  protected[aql] def parseSql(input: String): LogicalPlan = AqlParser.parse(input)

  @transient
  protected[aql] def executePlan(plan: LogicalPlan) = new QueryExecution(this,plan)

  @transient
  protected[aql] lazy val planner:SparkPlanner = new SparkPlanner(this)

  //views
  @transient
  protected lazy val views = new ViewTable

  def registerView(viewName: String, plan: LogicalPlan): Unit = {
    views.registerView(viewName,plan)
  }

  def lookforView(viewName: String): LogicalPlan = {
    views.lookforView(viewName)
  }

  def viewExists(viewName: String): Boolean = {
    views.viewExists(viewName)
  }


  def aql(input: String): View = {
    View(this, parseSql(input))
  }


}


/**
 * singleton
 */
object AqlContext{

  /**
   * The active AqlContext for the current thread.
   */
  private val activeContext: InheritableThreadLocal[AqlContext] =
    new InheritableThreadLocal[AqlContext]

  /**
   * Reference to the created AqlContext.
   */
  @transient private val instantiatedContext = new AtomicReference[AqlContext]()

  /**
   * Get the singleton AqlContext if it exists or create a new one using the given SparkContext.
   */
  def getOrCreate(sparkContext: SparkContext): AqlContext = {
    val ctx = activeContext.get()
    if (ctx != null && !ctx.sparkContext.isStopped) {
      return ctx
    }

    synchronized {
      val ctx = instantiatedContext.get()
      if (ctx == null || ctx.sparkContext.isStopped) {
        new AqlContext(sparkContext)
      } else {
        ctx
      }
    }
  }

  private[aql] def clearInstantiatedContext(): Unit = {
    instantiatedContext.set(null)
  }

  private[aql] def setInstantiatedContext(aqlContext: AqlContext): Unit = {
    synchronized {
      val ctx = instantiatedContext.get()
      if (ctx == null || ctx.sparkContext.isStopped) {
        instantiatedContext.set(aqlContext)
      }
    }
  }

  private[aql] def getInstantiatedContextOption(): Option[AqlContext] = {
    Option(instantiatedContext.get())
  }

  /**
   * Changes the AqlContext that will be returned in this thread and its children when
   * AqlContext.getOrCreate() is called. This can be used to ensure that a given thread receives
   * a AqlContext with an isolated session, instead of the global (first created) context.
   */
  def setActive(aqlContext: AqlContext): Unit = {
    activeContext.set(aqlContext)
  }

  /**
   * Clears the active AqlContext for current thread. Subsequent calls to getOrCreate will
   * return the first created context instead of a thread-local override.
   */
  def clearActive(): Unit = {
    activeContext.remove()
  }

  private[aql] def getActive(): Option[AqlContext] = {
    Option(activeContext.get())
  }

}