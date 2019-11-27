import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com"
ThisBuild / organizationName := "jasonnerothin"

lazy val root = (project in file("."))
  .settings(
    name := "avro-schema-g8r",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      avro
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
