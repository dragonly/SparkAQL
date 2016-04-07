package SparkAql.examples

import SparkAql.aql.AqlContext
import org.apache.spark.{SparkConf, SparkContext}

object Example {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val aqlContext = new AqlContext(sc)

    //set document
    val people = sc.textFile("people.txt")
    aqlContext.setDocument(people)

    val badBoys = Seq("Simon", "Suen")
    aqlContext.registerDictionary("badboys", badBoys)


    val dictView = aqlContext.aql("create view BadBoys as extract dictionary badboys as bbname from document")
    // \'regex\'
    val regexView = aqlContext.aql("create view GoodBoys as extract regex \'[M][\\p{Lower}]+\' as gbname from document")

    println("=====Dictionary View=====")
    println("schema:")
    dictView.printSchema()
    println("spans:")
    val bbTuple = dictView.rdd.collect()
    for(tuple <- bbTuple){
      for(span <- tuple.spans){
        println(span)
      }
    }
    println("text:")
    val bbText = dictView.textRdd.collect()
    for(elem <- bbText){
      println(elem)
    }

    println("=====Regex View=====")
    println("schema:")
    regexView.printSchema()
    println("spans:")
    val gbTuple = regexView.rdd.collect()
    for(tuple <- gbTuple){
      for(span <- tuple.spans){
        println(span)
      }
    }
    println("text:")
    val gbText = regexView.textRdd.collect()
    for(elem <- gbText){
      println(elem)
    }

  }
}
