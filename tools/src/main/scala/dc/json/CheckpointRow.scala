package dc.json

import spray.json.DefaultJsonProtocol

case class CheckpointRow(id: String,
                         key: (Int, String),
                         value: Checkpoint)

object CheckpointRow extends DefaultJsonProtocol {
  implicit val checkpointRowFormat = jsonFormat3(CheckpointRow.apply)
}