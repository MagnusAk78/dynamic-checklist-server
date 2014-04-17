import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "spray repo" at "http://repo.spray.io/",
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

  //Versions
  val myScalaVersion = "2.10.4"
  val akkaVersion = "2.2.0-RC1"
  val sprayVersion = "1.2-M8"
  val specs2Version = "1.14"
  val slf4jVersion = "1.7.5"
  val json4sVersion = "3.2.4"
  val logbackVersion = "1.0.12"
  val sprayJsonVersion = "1.2.5"

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val sprayClient   = "io.spray"                 %   "spray-client"    % sprayVersion
  val akkaActor     = "com.typesafe.akka"        %%  "akka-actor"      % akkaVersion
  val akkaSlf4      = "com.typesafe.akka"        %%  "akka-slf4j"      % akkaVersion
  val specs2        = "org.specs2"               %%  "specs2"          % specs2Version
  //Logging
  val slf4jApi      = "org.slf4j"                %   "slf4j-api"       % slf4jVersion
  val logback       = "ch.qos.logback"           %   "logback-classic" % logbackVersion
  val logbackcore   = "ch.qos.logback"           %   "logback-core"    % logbackVersion
  //Json
  val json4sNative  = "org.json4s"               %%  "json4s-native"   % json4sVersion
  val sprayJson     = "io.spray"                 %%  "spray-json"      % sprayJsonVersion

  //DependecyGroups
  val logDependencies = Seq(akkaSlf4, slf4jApi, logback, logbackcore)
  val akkaDependencies = Seq(akkaActor)
  val testDependencies = Seq(specs2 % "test")

  val dcHandleCheckpointsDependencies = Seq(sprayClient, json4sNative, sprayJson) ++ akkaDependencies ++ logDependencies ++ testDependencies
}