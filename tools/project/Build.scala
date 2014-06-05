import sbt._
import Keys._

object Build extends Build {
  import BuildSettings._
  import Dependencies._

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Main Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val main = Project("dctools",file("."))
    .settings(dcHandleCheckpointsSettings: _*)
    .settings(libraryDependencies ++= dcHandleCheckpointsDependencies)
}
