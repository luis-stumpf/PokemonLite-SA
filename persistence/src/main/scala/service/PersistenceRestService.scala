package service

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpEntity, ContentTypes }
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import scala.util.{ Failure, Success }
import play.api.libs.json.Json
import model.GameInterface
import model.impl.game.Game

import di.PersistenceModule.given_FileIOInterface as fileIO

class PersistenceRestService() {

  implicit val system: ActorSystem[?] =
    ActorSystem( Behaviors.empty, "SprayExample" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val route: Route =
    concat(
      get {
        pathSingleSlash {
          complete( "PokemonLite PersistenceApi" )
        }
      },
      post {
        path( "save" ) {
          entity( as[String] ) { input =>
            fileIO.save( Json.parse( input ) )
            complete( HttpEntity( ContentTypes.`application/json`, "saved" ) )
          }
        }
      },
      get {
        path( "load" ) {
          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              Json.stringify( fileIO.load.toJson )
            )
          )
        }
      }
    )

  def start(): Unit = {
    val binding = Http().newServerAt( "localhost", 9002 ).bind( route )

    binding.onComplete {
      case Success( binding ) =>
        println(
          s"PokemonLite PersistenceAPI service online at http://localhost:9002/"
        )
      case Failure( exception ) =>
        println(
          s"PokemonLite PersistenceAPI service failed to start: ${exception.getMessage}"
        )
    }
  }

}
