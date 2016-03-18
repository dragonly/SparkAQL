package SparkAql.examples

import SparkAql.aql.AqlContext
import org.apache.spark.{SparkConf, SparkContext}

object Example {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val aqlContext = new AqlContext(sc)

    val instrument = Seq("pipe", "flute")
    aqlContext.registerDictionary("instrument", instrument)
    val test1 = aqlContext.lookforDict("instrument")
    val test2 = aqlContext.aql("extract dictionary instrument as insInstance from document")

    println("Dictionary instrument: " + test1)
    println(test2)
  }
}
