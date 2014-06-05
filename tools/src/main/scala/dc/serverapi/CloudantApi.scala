package dc.serverapi

import dc.json._
import scala.concurrent.Future

class CloudantApi(val cloudantName: String, val databaseName: String) {

  private val baseUrl = "http://" + this.cloudantName + ".cloudant.com/" + databaseName + "/"

  private val checkpointsViewUrl = baseUrl + "_design/dc_server_app/_view/checkpoints"
  private val measurementViewUrl = "_design/dc_server_app/_view/measurements"

  private val quoteUrlStr = "%22"
  private val leftBracketUrlStr = "%5B"
  private val rightBracketUrlStr = "%5D"

  private def measurementsViewUrl(checkpointId: String): String = {
    val checkpointInQuotes = quoteUrlStr + checkpointId + quoteUrlStr
    baseUrl + measurementViewUrl + startKeyEndKeyUrl(withinBracketsString(checkpointInQuotes + ",{}"), withinBracketsString(checkpointInQuotes), true)
  }

  private def startKeyEndKeyUrl(startKey: String, endKey: String, descending: Boolean): String = {
    "?startkey=" + startKey + "&endkey=" + endKey + (if (descending) "&descending=true" else "")
  }

  private def withinBracketsString(input: String): String = {
    leftBracketUrlStr + input + rightBracketUrlStr
  }

  def checkpointSequence(): Future[Stream[Checkpoint]] = DataHandler.getCheckpointsFromServer(checkpointsViewUrl)

  def measurementSequence(checkpointId: String): Future[Stream[Measurement]] = DataHandler.getMeasurementsFromServer(measurementsViewUrl(checkpointId))
}

object CloudantApi {
  def apply(cloudantName: String, databaseName: String): CloudantApi = {
    new CloudantApi(cloudantName, databaseName)
  }
  
  def shutdown {
    DataHandler.shutdownSystem
  }
}