package dc.json

import spray.json.DefaultJsonProtocol
import spray.json.JsObject

case class Checkpoint(_id: String,
                      _rev: String,
                      type_of_obj: String,
                      active: Boolean,
                      checkpoint_name: String,
                      order_nr: Int,
                      description: String,
                      error_tag_1: String,
                      error_tag_2: String,
                      error_tag_3: String,
                      error_tag_4: String,
                      action_tag_1: String,
                      action_tag_2: String,
                      action_tag_3: String,
                      action_tag_4: String,
                      time_days: Int,
                      time_hours: Int,
                      exclude_weekends: Boolean,
                      _attachments: Option[JsObject]) {
    
  def valuesShort(separator: String): String = {
    _id + separator + checkpoint_name + separator + order_nr + separator + time_days + separator + time_hours + separator + exclude_weekends;  
  }
  
  override def toString: String = {
    "id : " + _id + ',' + " name : " + checkpoint_name + ',' + " order nr : " + order_nr + ',' + " days : " + time_days + ',' + " hours : " + time_hours + ',' + " exclude weekends : " + exclude_weekends
  }
}

object Checkpoint extends DefaultJsonProtocol {
  implicit val checkpointFormat = jsonFormat19(Checkpoint.apply)
  
  def keysShort(separator: String): String = {
    "id" + separator + "name" + separator + "order_nr" + separator + "time_days" + separator + "time_hours" + separator + "exclude_weekends";
  }
}