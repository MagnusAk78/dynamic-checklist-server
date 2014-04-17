package dc.json

import spray.json.DefaultJsonProtocol

case class MeasurementRow(id: String,
                         key: (String, Long),
                         value: Measurement)

object MeasurementRow extends DefaultJsonProtocol {
  implicit val MeasurementRowFormat = jsonFormat3(MeasurementRow.apply)
}