package SparkAql.aql.model

import SparkAql.aql.plan.logical.LogicalPlan
import SparkAql.aql.{AqlContext, QueryExecution}
import org.apache.spark.rdd.RDD

/**
 * abstract Relation of tuple
 */
class View(val aqlContext: AqlContext, val queryExecution: QueryExecution) extends Serializable{

  /**
   * A constructor that automatically analyzes the logical plan. and register view iff analyzed
   */
  def this(aqlContext: AqlContext, logicalPlan: LogicalPlan) = {
    this(aqlContext, {
      val qe = aqlContext.executePlan(logicalPlan)
      qe.assertAnalyzed()
      qe
    })
  }
  // view tuple schema
  def schema: Seq[String] = queryExecution.analyzed.schema

  def printSchema(): Unit = {
    for(elem <- schema){
      print(elem + " ")
    }
    println()
  }
  /**
   * Represents the content of the View as an RDD of Tuples.
   */
  lazy val rdd: RDD[Tuple] = {
    queryExecution.toRdd
  }

  lazy final val document = aqlContext.getDocument

  //one span rdd join docText
  lazy val textRdd: RDD[String] = {
    rdd.map[String](tuple =>{
      val span = tuple.spans.head
      document.substring(span.begin,span.end)
    }
    )
  }


  def printPlan(): Unit ={
    println(queryExecution.analyzed)
  }



}

object View {
  def apply(aqlContext: AqlContext, logicalPlan: LogicalPlan): View = {
    new View(aqlContext, logicalPlan)
  }
}
