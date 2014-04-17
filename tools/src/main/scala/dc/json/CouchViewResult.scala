package dc.json

import spray.json.{JsonFormat, DefaultJsonProtocol}

case class CouchViewResult[T](total_rows: Int, offset: Int, rows: List[T])

object CouchViewResult extends DefaultJsonProtocol {
  implicit def couchViewResultFormat[T: JsonFormat] = jsonFormat3(CouchViewResult.apply[T])
}