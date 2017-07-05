import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "net.rk02",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Firebase Scala"
  )

libraryDependencies ++= Seq(
  scalaTest % Test,
  "com.google.firebase" % "firebase-admin" % "4.1.7"
)
