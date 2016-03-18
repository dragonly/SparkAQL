package SparkAql.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import SparkAql.aql.parser.AqlParser

object Example{
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val test1 = AqlParser.parse("create view instrument")
    println(test1)
  }
}
