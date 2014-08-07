import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "twitter_fetcher"
  val appVersion = "0.1"

  scalaVersion := "2.10.3"

  val main = play.Project(appName, appVersion, Seq()).settings(
    Keys.fork in (Test) := false,
    // Add your own project settings here      
    libraryDependencies := Seq(
      cache,
      jdbc,
      filters,
      "com.typesafe.slick" %% "slick" % "2.0.1",
      "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.0",
      "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.play" %% "play-slick" % "0.6.0.1",
      "org.specs2" %% "specs2" % "2.3.10" % "test",
      "org.twitter4j" % "twitter4j-stream" % "3.0.3"
    ),
    resolvers := Seq("typesafe" at "http://repo.typesafe.com/typesafe/releases/"),
    slick <<= slickCodeGenTask // register manual sbt command
  )

  // code generation task
  lazy val slick = TaskKey[Seq[File]]("gen-tables")
  lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map {
    (dir, cp, r, s) => {
      val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
      val url = "jdbc:postgresql://localhost:5432/twitter_cat?user=root&password=root" // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
      val jdbcDriver = "org.postgresql.Driver"
      val slickDriver = "scala.slick.driver.PostgresDriver"
      val pkg = "schema"
      toError(r.run("scala.slick.model.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
      val fname = outputDir + "/schemaGeneration/Tables.scala"
      Seq(file(fname))
    }
  }

}