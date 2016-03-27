package SparkAql.aql

import SparkAql.aql.catalog.{ViewTable, Dictionary}
import SparkAql.aql.execution.QueryExecution
import SparkAql.aql.model.View
import SparkAql.aql.parser.AqlParser
import SparkAql.aql.plan.logical.LogicalPlan
import SparkAql.aql.analysis.Analyzer
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * AQL context: register Dic, execute AQL queries
  */

class AqlContext(@transient val sparkContext: SparkContext) {
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

  protected[aql] def parseSql(input: String): LogicalPlan = AqlParser.parse(input)

  protected[aql] def executePlan(plan: LogicalPlan) = new QueryExecution(this,plan)

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