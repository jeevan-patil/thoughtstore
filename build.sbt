name := """PlayReactiveMongoPolymer"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.0.play24",
  "org.specs2" %% "specs2-core" % "2.4.9" % "test",
  "org.specs2" %% "specs2-junit" % "2.4.9" % "test"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)


fork in run := true