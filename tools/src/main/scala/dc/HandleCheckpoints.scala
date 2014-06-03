package dc

import spray.http.HttpResponse
import dc.json.Checkpoint
import dc.json.Measurement
import dc.serverapi.CloudantApi
import scala.util.{ Success, Failure }
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import java.util.Date
import java.text.SimpleDateFormat

object HandleCheckpoints {
  
  var continue: Boolean = true

  abstract class MenuOption(text: String) {
    override def toString(): String = {
      text
    }

    def handle()
  }
  
  case object MenuSetDatabase extends MenuOption("Set database") {
    override def handle() {
      closeFiles()

      println("Cloudant database: ")
      val cloudantName = readLine
      println("Database name: ")
      currentCloudantApi = Some(CloudantApi(cloudantName, readLine))

      currentCloudantApi match {
        case Some(cloudantApi) => {
          currentCheckpointFile = Some(getFileWriteStream(getFileName(cloudantApi.cloudantName, cloudantApi.databaseName, "checkpoints")))
          currentMeasurementFile = Some(getFileWriteStream(getFileName(cloudantApi.cloudantName, cloudantApi.databaseName, "measurements")))

        }
        case None => { println("Not a valid server") }
      }
    }
  }
  
  case object MenuListCheckpoints extends MenuOption("List all checkpoints") {
    override def handle() {
      println("handleCheckpoints")
      currentCloudantApi match {
        case Some(cloudantApi) => {
          val checkpointStreamFuture = cloudantApi.checkpointSequence()

          val iterator = Await.result(checkpointStreamFuture, Duration.Inf).iterator
          while (iterator.hasNext) {
            println(iterator.next())
          }
        }
        case None => {}
      }
    }
  }
  
  case object MenuListMeasurements extends MenuOption("List measurements by checkpoint") {
    override def handle() {
      println("Checkpoint id: ")
      val checkpointId = readLine

      currentCloudantApi match {
        case Some(cloudantApi) => {
          val measurementStreamFuture = cloudantApi.measurementSequence(checkpointId)

          val iterator = Await.result(measurementStreamFuture, Duration.Inf).iterator
          while (iterator.hasNext) {
            println(iterator.next())
          }
        }
        case None => {}
      }
    }
  }
  
  case object MenuPrintAllToFile extends MenuOption("Print everything to file") {
    override def handle() {
      println("handlePrintAllToFile")

      currentCloudantApi match {
        case Some(cloudantApi) => {

          val checkpointFile = currentCheckpointFile.getOrElse(System.out)
          val measurementFile = currentMeasurementFile.getOrElse(System.out)

          val checkpointStreamFuture = cloudantApi.checkpointSequence()
          checkpointFile.println(Checkpoint.keysShort(SEPERATOR))
          measurementFile.println(Measurement.keysShort(SEPERATOR))

          val checkpointIterator = Await.result(checkpointStreamFuture, Duration.Inf).iterator

          while (checkpointIterator.hasNext) {
            val checkpoint: Checkpoint = checkpointIterator.next()
            checkpointFile.println(checkpoint.valuesShort(SEPERATOR))

            val measurementStreamFuture = cloudantApi.measurementSequence(checkpoint._id)

            val measurementIterator = Await.result(measurementStreamFuture, Duration.Inf).iterator
            while (measurementIterator.hasNext) {
              val measurement = measurementIterator.next()
              measurementFile.println(measurement.valuesShort(SEPERATOR, checkpoint.checkpoint_name))
            }
          }
        }
        case None => {}
      }
    }
  }
  
  case object MenuExit extends MenuOption("Exit") {
    override def handle() {
      closeFiles()
      continue = false
    }
  }

  private val SEPERATOR = ","

  private val menuOptions: List[MenuOption] = List(
    MenuSetDatabase,
    MenuListCheckpoints,
    MenuListMeasurements,
    MenuPrintAllToFile,
    MenuExit)

  private var currentCloudantApi: Option[CloudantApi] = None
  private var currentCheckpointFile: Option[java.io.PrintStream] = None
  private var currentMeasurementFile: Option[java.io.PrintStream] = None

  def main(args: Array[String]) {
    try {
      while (continue) {
        println()
        printMenu()
        handleInput()
      }
    } catch {
      case e: Exception => {
        println(e.getMessage())
        println()
        e.printStackTrace()
        System.exit(0)
      }
    }
  }

  private def getFileWriteStream(filename: String): java.io.PrintStream = {
    new java.io.PrintStream(new java.io.FileOutputStream(filename))
  }

  private def getFileName(server: String, database: String, typeOfData: String): String = {
    val sdf = new SimpleDateFormat("MM_dd");
    val dateObj = new Date()
    val dateString = sdf.format(dateObj);
    server + "_" + database + "_" + typeOfData + dateString + ".csv"
  }

  private def closeFiles() {
    currentCheckpointFile match {
      case Some(file) => {
        file.close()
        currentCheckpointFile = None
      }
      case None => {}
    }
    currentMeasurementFile match {
      case Some(file) => {
        file.close()
        currentMeasurementFile = None
      }
      case None => {}
    }
  }

  def handleInput() {
    val pos = readInt
    if (pos < 1 || pos > menuOptions.size) return ;

    menuOptions(pos - 1).handle()
  }

  private def printMenu() {
    println("HandleCheckpoints menu")
    println("")
    var i: Int = 1
    menuOptions.foreach((menuOption: MenuOption) => {
      println(i.toString + " " + menuOption.toString)
      i += 1
    })
  }
}
