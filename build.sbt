val libSpark = "org.apache.spark" %% "spark-core" % "1.6.1"

lazy val commonSettings = Seq(
  organization := "org.fudan",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val aql = (project in file("aql")).    
  settings(commonSettings: _*).
  settings(
    name := "Spark AQL",
    libraryDependencies += libSpark
  )

lazy val examples = (project in file("examples")).dependsOn(aql).
  settings(commonSettings: _*).
  settings(
    name:= "Spark AQL Examples"
  )