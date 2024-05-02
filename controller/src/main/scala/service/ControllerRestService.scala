package service

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpEntity, ContentTypes }
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import controller.ControllerInterface
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import scala.util.{ Failure, Success }
import play.api.libs.json.Json

class ControllerRestService( using controller: ControllerInterface ) {

  implicit val system: ActorSystem[?] =
    ActorSystem( Behaviors.empty, "SprayExample" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val routes = """<!DOCTYPE html>
<html>
<head>
    <title>ControllerRestService API Documentation</title>
</head>
<body>
    <h1>ControllerRestService API Documentation</h1>

    <h2>Endpoints</h2>

    <h3>POST /controller/command</h3>
    <p>This endpoint allows you to execute a command.</p>

    <h4>Request Parameters</h4>
    <ul>
        <li><strong>command</strong> (path parameter): The command to execute.</li>
        <li><strong>input</strong> (query parameter): Additional input for the command.</li>
    </ul>

    <h4>Response</h4>
    <p>The response is a JSON object. If the command is executed successfully, the JSON object represents the game state. If there's an error, the JSON object contains an "error" field with a description of the error.</p>

    <h4>Example</h4>
    <p>POST /controller/command?input=someInput</p>
</body>
</html>"""

  val route: Route =
    concat(
      get {
        pathSingleSlash {
          complete( HttpEntity( ContentTypes.`text/html(UTF-8)`, routes ) )
        }
      },
      get {
        path( "state" ) {
          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              controller.game.state.toString()
            )
          )
        }
      },
      get {
        path( "controller" / "game" ) {
          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              controller.game.toJson.toString()
            )
          )
        }
      },
      post {
        path( "controller" / Segment ) { command =>
          parameter( "input" ) { input =>
            command match {
              case "initPlayers" =>
                controller.doAndPublish( controller.initPlayers )
              case "addPlayer" =>
                controller.doAndPublish( controller.addPlayer, input )
              case "addPokemons" =>
                controller.doAndPublish( controller.addPokemons, input )
              case "next" =>
                controller.doAndPublish( controller.nextMove, input )
              case "attack" =>
                controller.doAndPublish( controller.attackWith, input )
              case "switchPokemon" =>
                controller.doAndPublish( controller.selectPokemon, input )
              case "gameOver" =>
                controller.doAndPublish( controller.restartTheGame )
              case "save" => controller.doAndPublish( controller.save )
              case "load" => controller.doAndPublish( controller.load )
              case "undo" => controller.doAndPublish( controller.undoMove )
              case "redo" => controller.doAndPublish( controller.redoMove )
              case _      => ""
            }

            controller.getGame() match {
              case Success( game ) =>
                complete(
                  HttpEntity(
                    ContentTypes.`application/json`,
                    game.toJson.toString()
                  )
                )
              case Failure( exception ) =>
                complete(
                  HttpEntity(
                    ContentTypes.`application/json`,
                    Json
                      .obj( "error" -> Json.toJson( exception.toString ) )
                      .toString()
                  )
                )
            }

          }
        }
      }
    )

  def start(): Unit = {
    val binding = Http().newServerAt( "0.0.0.0", 4001 ).bind( route )

    binding.onComplete {
      case Success( binding ) =>
        println(
          s"PokemonLite ControllerAPI service online at http://localhost:4001/"
        )
      case Failure( exception ) =>
        println(
          s"PokemonLite ControllerAPI service failed to start: ${exception.getMessage}"
        )
    }
  }

}
