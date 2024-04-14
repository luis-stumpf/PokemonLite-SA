val scala3Version = "3.4.1"

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := scala3Version,
  organization := "de.htwg.se",
  libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.18",
    "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0",
    ( "org.scala-lang.modules" %% "scala-swing" % "3.0.0" )
      .cross( CrossVersion.for3Use2_13 ),
    "org.scalafx" %% "scalafx" % "20.0.0-R31"
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
  }
)

lazy val persistence = ( project in file( "persistence" ) )
  .settings( commonSettings, name := "PokemonLitePersistence" )
  .dependsOn( model )

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
  .settings( commonSettings, name := "PokemonLiteController" )
  .dependsOn( model )
  .dependsOn( persistence )
  .aggregate( model )
  .aggregate( persistence )

lazy val tui = ( project in file( "tui" ) )
  .settings( commonSettings, name := "PokemonLiteTUI" )
  .dependsOn( controller )

lazy val gui = ( project in file( "gui" ) )
  .settings( commonSettings, name := "PokemonLiteGUI" )
  .dependsOn( controller )

lazy val root = ( project in file( "." ) )
  .settings(
    commonSettings,
    name := "PokemonLite",
    mainClass := Some( "PokemonLite" )
  )
  .dependsOn( util, model, controller, tui, gui, persistence )
  .aggregate( util, model, controller, tui, gui, persistence )

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
}

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

jacocoExcludes := Seq(
  "de.htwg.se.pokelite.aview*",
  "de.htwg.se.pokelite.util.Observable*",
  "de.htwg.se.pokelite.model.FieldInterface",
  "de.htwg.se.pokelite.PokemonLite*"
)

assemblyMergeStrategy in assembly := {
  case PathList( "META-INF", _* ) => MergeStrategy.discard
  case _                          => MergeStrategy.first
}

commands += Command.command( "clear" ) { state =>
  print( "\033c" )
  state
}
