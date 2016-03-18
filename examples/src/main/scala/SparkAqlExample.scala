<<<<<<< HEAD
import org.apache.spark.{SparkConf, SparkContext}
import SparkAql.aql.parser.AqlParser
=======
package SparkAql.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
>>>>>>> 490a41accf6b59692ed687ecaeff7d797b90fd68

import SparkAql.aql.parser.AqlParser

object Example{
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
<<<<<<< HEAD
    val aqlContext = new AqlContext(sc)

    val instrument = Seq("pipe","flute")
    aqlContext.registerDictionary("instrument", instrument)
    println(aqlContext.lookforDic("instrument"))

=======
>>>>>>> 490a41accf6b59692ed687ecaeff7d797b90fd68
    val test1 = AqlParser.parse("create view instrument")
    println(test1)
  }
}
