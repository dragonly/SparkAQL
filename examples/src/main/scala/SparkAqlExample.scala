import org.apache.spark.{SparkConf, SparkContext}
import SparkAql.aql.parser.AqlParser

object SparkAqlExample{
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val aqlContext = new AqlContext(sc)

    val instrument = Seq("pipe","flute")
    aqlContext.registerDictionary("instrument", instrument)
    println(aqlContext.lookforDic("instrument"))

    val test1 = AqlParser.parse("create view instrument")
    println(test1)

  }
}
