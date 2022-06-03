ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.1"

lazy val root = (project in file("."))
  .settings(
    name := "PokemonLite",
    idePackagePrefix := Some("de.htwg.se.pokelite")
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"
libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"
libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R24"

libraryDependencies ++= {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
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

commands += Command.command("clear") { state =>
  print("\033c")
  state
}


