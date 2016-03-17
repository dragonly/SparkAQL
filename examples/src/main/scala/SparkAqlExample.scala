import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.fudan.SparkAql.aql._
import parser.AqlParser

object SparkAqlExample{
  def main(args: Array[String]) {
    val hi = Hi
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    hi.hi
    val test = AqlParser.parse("create view instrument")
    println(test)
  }
}
