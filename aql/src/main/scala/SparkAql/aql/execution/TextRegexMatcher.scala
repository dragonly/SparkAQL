package SparkAql.aql.execution

import java.util.regex.Pattern

import scala.collection.mutable.ArrayBuffer

/**
 *
 *  matcher.start: inclusive; matcher.end: exclusive
 *  SubString(start,end) : start: inclusive; end: exclusive
 *
 */
object TextRegexMatcher {

  def apply(input: String,regex: String): Array[Segment] = {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(input)
    var matchList = ArrayBuffer[Segment]()

    while(matcher.find()){
      val text: String = input.subSequence(matcher.start, matcher.end).toString
      matchList += Segment(text,matcher.start,matcher.end)
    }

    return matchList.toArray

  }

}
