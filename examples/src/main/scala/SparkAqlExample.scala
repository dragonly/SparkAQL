import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.fudan.SparkAql.aql._

object SparkAqlExample{
  def main(args: Array[String]) {
    val hi = Hi
    hi.hi
  }
}
