package SparkAql.aql.plan.physical

import org.apache.spark.rdd.RDD
import SparkAql.aql.model.Tuple

case class PhysicalDictView(dictName: String) extends LeafSparkNode{

  override def doExecute(): RDD[Tuple] = {
    //a lot to do tomorrow

  }

}
