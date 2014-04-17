package dc.serverapi

import dc.json._
import scala.concurrent.Future

class CloudantApi(val cloudantName: String, val databaseName: String) {

  private val baseUrl = "http://" + this.cloudantName + ".cloudant.com/" + databaseName + "/"
  
  private val checkpointsViewUrl = baseUrl + "_design/dc_server_app/_view/checkpoints"
  
  private def measurementsViewUrl(checkpointId: String): String = {
    baseUrl + "_design/dc_server_app/_view/measurements" + "?startkey=[%22" + checkpointId + "%22,{}]&endkey=[%22" + checkpointId + "%22]&descending=true"
  }

  def checkpointSequence(): Future[Stream[Checkpoint]] = DataHandler.getCheckpointsFromServer(checkpointsViewUrl)

  def measurementSequence(checkpointId: String): Future[Stream[Measurement]] = DataHandler.getMeasurementsFromServer(measurementsViewUrl(checkpointId))
}

object CloudantApi {
  def apply(cloudantName: String, databaseName: String): CloudantApi = {
    new CloudantApi(cloudantName, databaseName)
  }
}