import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.fudan.SparkAql.aql._

object SparkAqlExample{
  def main(args: Array[String]) {
    val hi = Hi
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    hi.hi
  }
}
