name := """mummy"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.7"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "io.argonaut" %% "argonaut" % "6.3.7"
libraryDependencies += "com.auth0" % "mvc-auth-commons" % "1.8.0"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.5"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
