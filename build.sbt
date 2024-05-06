import com.github.sbt.jacoco.report.JacocoReportFormats
import com.typesafe.sbt.packager.docker._
val scala3Version = "3.3.3"
val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.2"

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := scala3Version,
  organization := "de.htwg.se",
  resolvers += "Akka library repository".at( "https://repo.akka.io/maven" ),
  dockerBaseImage := "openjdk:19",
  libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.18",
    "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0",
    ( "org.scala-lang.modules" %% "scala-swing" % "3.0.0" )
      .cross( CrossVersion.for3Use2_13 ),
    "org.scalafx" %% "scalafx" % "20.0.0-R31",
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "org.slf4j" % "slf4j-nop" % "2.0.5"
  ),
  libraryDependencies ++= {
    // Determine OS version of JavaFX binaries
    lazy val osName = System.getProperty( "os.name" ) match {
      case n if n.startsWith( "Linux" )   => "linux"
      case n if n.startsWith( "Mac" )     => "mac"
      case n if n.startsWith( "Windows" ) => "win"
      case _ => throw new Exception( "Unknown platform!" )
    }
    Seq( "base", "controls", "fxml", "graphics", "media", "swing", "web" )
      .map( m => "org.openjfx" % s"javafx-$m" % "20" )
  },
  libraryDependencies ++= {
    // Determine OS version of JavaFX binaries
    lazy val osName = System.getProperty( "os.name" ) match {
      case n if n.startsWith( "Linux" )   => "linux"
      case n if n.startsWith( "Mac" )     => "mac"
      case n if n.startsWith( "Windows" ) => "win"
      case _ => throw new Exception( "Unknown platform!" )
    }
    Seq( "base", "controls", "fxml", "graphics", "media", "swing", "web" )
      .map( m => "org.openjfx" % s"javafx-$m" % "20" )
  },
  jacocoExcludes := Seq(
    "*gui*",
    "*tui.TUI*",
    "*util.Observable*",
    "*util.UndoManager*",
    "*main*",
    "*di*",
    "*service*",
    "*client*"
  )
)

lazy val persistence = ( project in file( "persistence" ) )
  .settings(
    commonSettings,
    name := "PokemonLitePersistence",
    version := "1.0.0",
    dockerExposedPorts := Seq( 4002 )
  )
  .dependsOn( model, util )
  .enablePlugins( DockerPlugin, JavaAppPackaging )

lazy val util = ( project in file( "util" ) )
  .settings( commonSettings, name := "PokemonLiteUtil" )

lazy val model = ( project in file( "model" ) )
  .settings(
    commonSettings,
    name := "PokemonLiteModel",
    libraryDependencies ++= Seq(
      ( "com.typesafe.play" %% "play-json" % "2.8.2" )
        .cross( CrossVersion.for3Use2_13 ),
      "org.scala-lang.modules" %% "scala-xml" % "2.0.1"
    )
  )
  .dependsOn( util )

lazy val controller = ( project in file( "controller" ) )
  .settings(
    commonSettings,
    name := "PokemonLiteController",
    dockerExposedPorts := Seq( 4001 ),
    version := "1.0.0"
  )
  .dependsOn( model )
  .dependsOn( persistence )
  .aggregate( model )
  .aggregate( persistence )
  .enablePlugins( DockerPlugin, JavaAppPackaging )

lazy val tui = ( project in file( "tui" ) )
  .settings(
    commonSettings,
    name := "PokemonLiteTUI",
    dockerExposedPorts := Seq( 4003 ),
    version := "1.0.0"
  )
  .dependsOn( controller, model, util )
  .enablePlugins( DockerPlugin, JavaAppPackaging )

lazy val gui = ( project in file( "gui" ) )
  .settings(
    commonSettings,
    name := "PokemonLiteGUI",
    dockerExposedPorts := Seq( 4004 ),
    version := "1.0.0"
  )
  .dependsOn( controller )

lazy val root = ( project in file( "." ) )
  .settings(
    commonSettings,
    name := "PokemonLite",
    version := "1.0.0",
    dockerExposedPorts := Seq( 4003 ),
    mainClass := Some( "PokemonLite" ),
    jacocoAggregateReportSettings := JacocoReportSettings(
      title = "Project Coverage",
      formats = Seq( JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML )
    )
  )
  .dependsOn( util, model, controller, tui, gui, persistence )
  .aggregate( util, model, controller, tui, gui, persistence )
  .enablePlugins( DockerPlugin, JavaAppPackaging )

jacocoReportSettings := JacocoReportSettings(
  "Jacoco Coverage Report",
  None,
  JacocoThresholds(),
  Seq(
    JacocoReportFormats.ScalaHTML,
    JacocoReportFormats.XML
  ), // note XML formatter
  "utf-8"
)

assemblyMergeStrategy in assembly := {
  case PathList( "META-INF", _* ) => MergeStrategy.discard
  case _                          => MergeStrategy.first
}

commands += Command.command( "clear" ) { state =>
  print( "\033c" )
  state
}
