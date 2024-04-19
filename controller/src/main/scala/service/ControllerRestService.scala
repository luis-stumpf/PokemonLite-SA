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
    val binding = Http().newServerAt( "localhost", 4001 ).bind( route )

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
