import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "net.rk02",
      scalaVersion := "2.11.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Carlo"
  )

libraryDependencies ++= Seq(
  scalaTest % Test,
  "org.json4s" %% "json4s-jackson" % "3.3.0",
  "com.google.firebase" % "firebase-admin" % "4.1.7"
)
