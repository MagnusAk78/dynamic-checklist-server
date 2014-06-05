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
  implicit val system = ActorSystem("akka-spray")
  import system.dispatcher

  private val LEFT_BRACE = "{"

  private val pipelineGetCheckpoints: HttpRequest => Future[Stream[Checkpoint]] = sendReceive ~> convertPlainTextResponseToApplicationJsonResponse ~> unmarshal[CouchViewResult[CheckpointRow]] ~> convertCheckpointViewResultToStream
  
  private val pipelineGetMeasurements: HttpRequest => Future[Stream[Measurement]] = sendReceive ~> convertPlainTextResponseToApplicationJsonResponse ~> unmarshal[CouchViewResult[MeasurementRow]] ~> convertMeasurementViewResultToStream

  private def convertPlainTextResponseToApplicationJsonResponse(response: HttpResponse): HttpResponse = {
    val entityString = response.entity.asString(HttpCharsets.`UTF-8`)
    val jsonString = entityString.substring(entityString.indexOf(LEFT_BRACE))
    response.withEntity(HttpEntity(ContentTypes.`application/json`, jsonString))
  }
  
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
    pipelineGetMeasurements(Get(getMeasurementsUrl))    
  }
  
  def shutdownSystem {
    system.shutdown
  }
}
