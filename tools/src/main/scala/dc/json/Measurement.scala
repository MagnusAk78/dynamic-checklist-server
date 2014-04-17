package dc.json

import spray.json.DefaultJsonProtocol
import spray.json.JsObject
import java.util.Date
import java.text.SimpleDateFormat

case class Measurement(_id: String,
  _rev: String,
  checkpoint: String,
  date: Long,
  type_of_obj: String,
  value: Int,
  tag: Option[String]) {

  def valuesShort(separator: String, checkpointName: String): String = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val dateObj = new Date(date)
    val dateString = sdf.format(dateObj);
    checkpointName + separator + dateString + separator + value + separator + tag.getOrElse("")
  }
}

object Measurement extends DefaultJsonProtocol {
  implicit val measurementFormat = jsonFormat7(Measurement.apply)

  def keysShort(separator: String): String = {
    "checkpoint" + separator + "timestamp" + separator + "value" + separator + "tag"
  }
}