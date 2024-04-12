val scala3Version = "3.3.3"

Compile / mainClass := Some( "pokelite.PokemonLite" )

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := scala3Version,
  libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.18",
    "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
  )
)

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
  .aggregate( util )

lazy val root = ( project in file( "." ) )
  .settings(
    commonSettings,
    name := "PokemonLite",
    mainClass := Some( "pokelite.PokemonLite" )
  )
  .dependsOn( util, model )
  .aggregate( util, model )

libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"

libraryDependencies += ( "org.scala-lang.modules" %% "scala-swing" % "3.0.0" )
  .cross( CrossVersion.for3Use2_13 )

assemblyJarName in assembly := "baeldung-scala-sbt-assembly-fatjar-1.0.jar"

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
