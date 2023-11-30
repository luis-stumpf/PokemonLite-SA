ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.1"

Compile/mainClass := Some("de.htwg.se.pokelite.PokemonLite")
mainClass in (Compile, packageBin) := Some("de.htwg.se.pokelite.PokemonLite")

lazy val root = (project in file("."))
  .settings(
    name := "PokemonLite",
    idePackagePrefix := Some("de.htwg.se.pokelite")
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"
libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"

libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0").cross(CrossVersion.for3Use2_13)
libraryDependencies += ( "net.codingwell" %% "scala-guice" % "5.0.2" ).cross( CrossVersion.for3Use2_13 )

libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.8.2").cross( CrossVersion.for3Use2_13 )
libraryDependencies += ("org.scala-lang.modules" %% "scala-xml" % "2.0.1")

assemblyJarName in assembly := "baeldung-scala-sbt-assembly-fatjar-1.0.jar"

libraryDependencies ++= {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "20")
}

jacocoReportSettings := JacocoReportSettings(
  "Jacoco Coverage Report",
  None,
  JacocoThresholds(),
  Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML), // note XML formatter
  "utf-8")

jacocoExcludes := Seq(
  "de.htwg.se.pokelite.aview*",
  "de.htwg.se.pokelite.util.Observable*",
  "de.htwg.se.pokelite.model.FieldInterface",
  "de.htwg.se.pokelite.PokemonLite*"

)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}

commands += Command.command("clear") { state =>
  print("\033c")
  state
}


