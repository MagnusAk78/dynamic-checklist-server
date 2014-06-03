import sbt._
import Keys._
import com.github.retronym.SbtOneJar

object BuildSettings {
  val VERSION = "0.1"

  lazy val basicSettings = seq(
    version               := VERSION,
    organization          := "chalmers",
    description           := "Tools for dynamic checklist server",
    startYear             := Some(2013),
    scalaVersion          := Dependencies.myScalaVersion,    
    exportJars 			  := true,
    resolvers             ++= Dependencies.resolutionRepos,
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.7",
      "-language:_",
      "-Xlog-reflective-calls"
    )    
  )

  lazy val dcHandleCheckpointsSettings = basicSettings ++ SbtOneJar.oneJarSettings
}