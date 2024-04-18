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

  implicit val system: ActorSystem[_] =
    ActorSystem( Behaviors.empty, "SprayExample" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val route: Route =
    concat(
      get {
        pathSingleSlash {
          complete( "PokemonLite ControllerAPI" )
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
      post {
        path( "controller" / Segment ) { command =>
          parameter( "input" ) { input =>
            val gameTry = command match {
              case "initPlayers" =>
                controller.handleRequest( controller.initPlayers )
              case "addPlayer" =>
                controller.handleRequest( controller.addPlayer, input )
              case "addPokemons" =>
                controller.handleRequest( controller.addPokemons, input )
              case "next" =>
                controller.handleRequest( controller.nextMove, input )
              case "attack" =>
                controller.handleRequest( controller.attackWith, input )
              case "switchPokemon" =>
                controller.handleRequest( controller.selectPokemon, input )
              case "gameOver" =>
                controller.handleRequest( controller.restartTheGame )
              case "save" => controller.handleRequest( controller.save )
              case "load" => controller.handleRequest( controller.load )
              case "undo" => controller.handleRequest( controller.undoMove )
              case "redo" => controller.handleRequest( controller.redoMove )
              case _      => ""
            }

            gameTry match {
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
              case _: String =>
                complete(
                  HttpEntity(
                    ContentTypes.`application/json`,
                    "Invalid command"
                  )
                )
            }

          }
        }
      }
    )

  def start(): Unit = {
    val binding = Http().newServerAt( "localhost", 9001 ).bind( route )

    binding.onComplete {
      case Success( binding ) =>
        println(
          s"PokemonLite ControllerAPI service online at http://localhost:9001/"
        )
      case Failure( exception ) =>
        println(
          s"PokemonLite ControllerAPI service failed to start: ${exception.getMessage}"
        )
    }
  }

}
