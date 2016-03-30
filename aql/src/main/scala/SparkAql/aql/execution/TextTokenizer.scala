package SparkAql.aql.execution

import java.util.regex.Pattern

import scala.collection.mutable.ArrayBuffer

/**

 * @param text
 * @param start   the beginning index, inclusive.
 * @param end    the ending index, exclusive.
 *
 */

case class Segment(val text: String, val start: Int, val end: Int)

/**
 *  matcher.start: inclusive; matcher.end: exclusive
 *  SubString(start,end) : start: inclusive; end: exclusive
 */
object TextTokenizer {
  def apply(input: String,regex: String): Array[Segment] = {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(input)
    var index: Int = 0
    var matchList = ArrayBuffer[Segment]()

    while(matcher.find()){
      val text: String = input.subSequence(index, matcher.start).toString
      matchList += Segment(text,index,matcher.start)
      index = matcher.end
    }

    if(index != input.length-1 ){
      val text: String = input.subSequence(index, input.length).toString
      matchList += Segment(text,index,input.length)
    }

    if(index == 0){
      return Array(Segment(input,0,input.length))
    }

    return matchList.toArray

  }

}
