name := """gestion-estudiantes"""
organization := "adrian.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

//libraryDependencies += guice
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "com.h2database" % "h2" % "2.2.220",
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "adrian.com.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "adrian.com.binders._"
