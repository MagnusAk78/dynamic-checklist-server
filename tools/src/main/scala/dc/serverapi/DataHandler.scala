package dc.serverapi

import spray.http._
import scala.concurrent.Future
import dc.json._
import spray.client.pipelining._
import spray.http.HttpRequest
import spray.http.HttpResponse
import akka.actor.ActorSystem
import scala.util.{Failure, Success}
import spray.httpx.marshalling._
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller

protected object DataHandler {
  implicit val system = ActorSystem()
  import system.dispatcher

  private val LEFT_BRACE = "{"

  private val pipelineGetCheckpoints: HttpRequest => Future[Stream[Checkpoint]] = sendReceive ~> convertPlainTextResponseToApplicationJsonResponse ~> unmarshal[CouchViewResult[CheckpointRow]] ~> convertCheckpointViewResultToStream
  
  private val pipelineGetMeasurements: HttpRequest => Future[Stream[Measurement]] = sendReceive ~> convertPlainTextResponseToApplicationJsonResponse ~> unmarshal[CouchViewResult[MeasurementRow]] ~> convertMeasurementViewResultToStream
  
  //private val pipelineGetCheckpoint: HttpRequest => Future[Checkpoint] = sendReceive ~> convertPlainTextResponseToApplicationJsonResponse ~> unmarshal[Checkpoint]

  //private val pipelineUpdateCheckpoint: Checkpoint => Future[HttpResponse] = convertCheckpointToHttpRequest(_) ~> sendReceive

  private def convertPlainTextResponseToApplicationJsonResponse(response: HttpResponse): HttpResponse = {
    val entityString = response.entity.asString(HttpCharsets.`UTF-8`)
    val jsonString = entityString.substring(entityString.indexOf(LEFT_BRACE))
    response.withEntity(HttpEntity(ContentTypes.`application/json`, jsonString))
  }

  /*
  private def convertCheckpointToHttpRequest(url: String) = {
    val newEntity = marshal(checkpoint) match {
      case Left(x) => {
        throw x
      }
      case Right(x) => x
    }
    HttpRequest.apply(method = HttpMethods.PUT, uri = BASE_URL(cloudantDatabase) + checkpoint._id, entity = newEntity)
  } 
  */
  
  private def convertCheckpointViewResultToStream(viewResult: CouchViewResult[CheckpointRow]): Stream[Checkpoint] = {
    viewResult.rows.map {_.value}.toList.toStream
  }
  
  private def convertMeasurementViewResultToStream(viewResult: CouchViewResult[MeasurementRow]): Stream[Measurement] = {
    viewResult.rows.map {_.value}.toList.toStream
  }

  def getCheckpointsFromServer(getCheckpointsUrl: String) = {
    pipelineGetCheckpoints(Get(getCheckpointsUrl)) 
  }
  
  def getMeasurementsFromServer(getMeasurementsUrl: String) = {
    pipelineGetMeasurements(
      Get(getMeasurementsUrl)
    )    
  }

  /*
  def sendUpdatedCheckpointToServer(checkpoint: Checkpoint, successCallback: ((HttpResponse) => Unit), failureCallback: ((Throwable) => Unit)) = {
    pipelineUpdateCheckpoint(checkpoint) onComplete {
      case Success(response) => successCallback(response)
      case Failure(error) => failureCallback(error)
    }
  }
  */
}
