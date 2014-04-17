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

abstract class MenuOption(text: String) {
  override def toString(): String = {
    text
  }
}
case object MenuSetDatabase extends MenuOption("Set database")
case object MenuListCheckpoints extends MenuOption("List all checkpoints")
case object MenuListMeasurements extends MenuOption("List measurements by checkpoint")
case object MenuPrintAllToFile extends MenuOption("Print everything to file")
case object MenuExit extends MenuOption("Exit")

object HandleCheckpoints {

  private val SEPERATOR = ","

  private val menuOptions: List[MenuOption] = List(
    MenuSetDatabase,
    MenuListCheckpoints,
    MenuListMeasurements,
    MenuPrintAllToFile,
    MenuExit)

  private var cloudantApi: CloudantApi = null
  private var currentCheckpointFile: java.io.PrintStream = null
  private var currentMeasurementFile: java.io.PrintStream = null

  def main(args: Array[String]) {
    try {
      while (true) {
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
  
  private def closeFiles() {
    if (null != currentCheckpointFile) {
          currentCheckpointFile.close()
        }
    if (null != currentMeasurementFile) {
          currentMeasurementFile.close()
        }
  }

  def handleInput() {
    val pos = readInt
    if (pos < 1 || pos > menuOptions.size) return ;

    menuOptions(pos - 1) match {
      case MenuSetDatabase => handleSetDatabase()
      case MenuListCheckpoints => handleListCheckpoints()
      case MenuListMeasurements => handleListMeasurements()
      case MenuPrintAllToFile => handlePrintAllToFile()
      case MenuExit => {
        closeFiles()
        System.exit(0)
      }
    }
  }

  def handleSetDatabase() {
    closeFiles()

    println("Cloudant database: ")
    val cloudantName = readLine
    println("Database name: ")
    cloudantApi = CloudantApi(cloudantName, readLine)
    val sdf = new SimpleDateFormat("MM_dd");
    val dateObj = new Date()
    val dateString = sdf.format(dateObj);
    currentCheckpointFile = getFileWriteStream(cloudantApi.cloudantName + "_" + cloudantApi.databaseName + "_" + "checkpoints" + dateString + ".csv")
    currentMeasurementFile = getFileWriteStream(cloudantApi.cloudantName + "_" + cloudantApi.databaseName + "_"+ "measurements" + dateString + ".csv")
  }

  private def handleListCheckpoints() {
    println("handleCheckpoints")

    val checkpointStreamFuture = cloudantApi.checkpointSequence()

    val iterator = Await.result(checkpointStreamFuture, Duration.Inf).iterator
    while (iterator.hasNext) {
      println(iterator.next())
    }
  }

  def handleListMeasurements() {
    println("Checkpoint id: ")
    val checkpointId = readLine

    val measurementStreamFuture = cloudantApi.measurementSequence(checkpointId)

    val iterator = Await.result(measurementStreamFuture, Duration.Inf).iterator
    while (iterator.hasNext) {
      println(iterator.next())
    }
  }

  private def handlePrintAllToFile() {
    println("handlePrintAllToFile")

    val checkpointStreamFuture = cloudantApi.checkpointSequence()
    currentCheckpointFile.println(Checkpoint.keysShort(SEPERATOR))
    currentMeasurementFile.println(Measurement.keysShort(SEPERATOR))

    val checkpointIterator = Await.result(checkpointStreamFuture, Duration.Inf).iterator
    while (checkpointIterator.hasNext) {
      val checkpoint: Checkpoint = checkpointIterator.next()
      currentCheckpointFile.println(checkpoint.valuesShort(SEPERATOR))
      val measurementStreamFuture = cloudantApi.measurementSequence(checkpoint._id)

      val measurementIterator = Await.result(measurementStreamFuture, Duration.Inf).iterator
      while (measurementIterator.hasNext) {
        val measurement = measurementIterator.next()
        currentMeasurementFile.println(measurement.valuesShort(SEPERATOR, checkpoint.checkpoint_name))
      }
    }
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