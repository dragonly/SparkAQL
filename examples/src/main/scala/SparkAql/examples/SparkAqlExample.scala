package SparkAql.examples

import SparkAql.aql.AqlContext
import org.apache.spark.{SparkConf, SparkContext}

object Example {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val aqlContext = new AqlContext(sc)

    //set document
    val people = sc.textFile("file:///home/simon/Code/test/people.txt")
    aqlContext.setDocument(people)

    val badBoys = Seq("Simon", "Suen")
    aqlContext.registerDictionary("badboys", badBoys)


    val bbView = aqlContext.aql("extract dictionary badboys as BadBoys from document")
    bbView.printPlan()

  }
}
