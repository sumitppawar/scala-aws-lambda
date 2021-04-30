scalaVersion := "2.13.5"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

lazy val root = (project in file(".")).
  settings(
    name := "scala-aws-lambda",
    version := "1.0",
    scalaVersion := "2.13.5",
    retrieveManaged := true,
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.0.0",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "1.0.0",
    libraryDependencies += "io.circe" %% "circe-core" % "0.12.3",
    libraryDependencies += "io.circe" %% "circe-generic" % "0.12.3",
    libraryDependencies += "io.circe" %% "circe-parser" % "0.12.3",
    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.3",
    libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4",
    libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
    libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.17"
  )