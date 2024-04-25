import com.github.sbt.jacoco.report.JacocoReportFormats

val scala3Version = "3.4.1"
val AkkaVersion = "2.9.2"
val AkkaHttpVersion = "10.6.2"

// Github Containers Stuff Settings
ThisBuild / resolvers += "GitHub PokemonLite Packages" at "https://ghcr.io/PokemonLite" // oder muss Luis da auch noch rein
// ThisBuild / resolvers += Resolver.url("GitHub Packages", url("https://ghcr.io/PokemonLite"))(Resolver.ivyStylePatterns)
ThisBuild / publishTo := Some("GitHub PokemonLite Packages" at "https://ghcr.io/PokemonLite")
ThisBuild / credentials ++= {
  val credentials = Path.userHome / ".sbt" / ".credentials"
  Seq(
    credentials match {
      case credFile if credFile.exists() => Credentials(credFile)
      case _ => Credentials(
        "GitHub Containers Registry",
        "ghcr.io",
        System.getenv("GITHUB_USERNAME"),
        System.getenv("GITHUB_TOKEN")
      )
    }
  )
}

// Docker Settings Stuff
val dockerPublishToDockerHub = taskKey[Unit]("Publish Docker image to DockerHub")
val dockerPublishToGHCR = taskKey[Unit]("Publish Docker image to GitHub Container Registry")
val docker_username = System.getenv("GITHUB_USERNAME") // Wenn github mit docker verbunden

dockerPublishToDockerHub := {
  ThisBuild / dockerUpdateLatest := true
  ThisBuild / dockerBaseImage := "openjdk:17-jdk"
  ThisBuild / Docker / dockerRepository := Some("docker.io")
  ThisBuild / publishLocal.value
  ThisBuild / Docker / publish.value
  ThisBuild / Docker / dockerUsername := Some(docker_username)
}

dockerPublishToGHCR := {
  ThisBuild / dockerUpdateLatest := true
  ThisBuild / dockerBaseImage := "openjdk:17-jdk"
  ThisBuild / Docker / dockerRepository := Some("ghcr.io")
  ThisBuild / publishLocal.value
  ThisBuild / Docker / publish.value
  ThisBuild / Docker / dockerUsername := Some(docker_username)
}

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := scala3Version,
  organization := "de.htwg.se",
  resolvers += "Akka library repository".at( "https://repo.akka.io/maven" ),
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
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
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
    Compile / mainClass := Some("service.PersistenceRestService"),
    dockerExposedPorts ++= Seq(9091)
  )
  .dependsOn( model, util )
  .enablePlugins(DockerPlugin, JavaAppPackaging)

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
    Compile / mainClass := Some("service.ControllerRestService"), // check paths!!!
    dockerExposedPorts ++= Seq(9090),
  )
  .dependsOn( model )
  .dependsOn( persistence )
  .aggregate( model )
  .aggregate( persistence )
  .enablePlugins(DockerPlugin, JavaAppPackaging)

lazy val tui = ( project in file( "tui" ) )
  .settings(
    commonSettings,
    name := "PokemonLiteTUI" ,
    Compile / mainClass := Some("service.TUIService")
    // run / connectInput := true,
  )
  .dependsOn( controller )
  .enablePlugins(DockerPlugin, JavaAppPackaging)

lazy val gui = ( project in file( "gui" ) )
  .settings(
    commonSettings,
    name := "PokemonLiteGUI",
    Compile / mainClass := Some("service.GUIService") // check paths!!!
  )
  .dependsOn( controller )

lazy val root = ( project in file( "." ) )
  .settings(
    commonSettings,
    name := "PokemonLite",
    mainClass := Some( "PokemonLite" ),
    jacocoAggregateReportSettings := JacocoReportSettings(
      title = "Project Coverage",
      formats = Seq( JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML )
    ),
    publishArtifact := false,
    /*run := {
      (gui / Compile / run).evaluated
      (tui / Compile / run).evaluated
      (controller / Compile / run).evaluated
      (persistence / Compile / run).evaluated
    }*/
  )
  .dependsOn( util, model, controller, tui, gui, persistence )
  .aggregate( util, model, controller, tui, gui, persistence )

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
