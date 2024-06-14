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

import fileIo.FileIOInterface
import di.PersistenceRestModule.given_DAOInterface as dao
import database.slick.defaultImpl.SlickDAO.delete
import database.mongo.MongoDAO.delete
import database.couch.CouchDAO.delete

class PersistenceRestService( using fileIO: FileIOInterface ) {

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
            val game = Game.fromJson( Json.parse( input ) )
            dao.save( game )
            complete( HttpEntity( ContentTypes.`application/json`, "saved" ) )
          }
        }
      },
      get {
        path( "load" ) {
          println( "loading" )
          var game: GameInterface = dao.load().get

          complete(
            HttpEntity(
              ContentTypes.`application/json`,
              Json.stringify( game.toJson )
            )
          )
        }
      },
      put {
        path( "delete" ) {
          dao.delete( None )
          complete( HttpEntity( ContentTypes.`application/json`, "deleted" ) )
        }
      },
      put {
        path( "update" ) {
          entity( as[String] ) { input =>
            val game = Game.fromJson( Json.parse( input ) )
            dao.update( None, game )
            complete( HttpEntity( ContentTypes.`application/json`, "updated" ) )
          }
        }
      }
    )

  def start(): Unit = {
    val binding = Http().newServerAt( "0.0.0.0", 4002 ).bind( route )

    binding.onComplete {
      case Success( binding ) =>
        println(
          s"PokemonLite PersistenceAPI service online at http://localhost:4002/"
        )
      case Failure( exception ) =>
        println(
          s"PokemonLite PersistenceAPI service failed to start: ${exception.getMessage}"
        )
    }
  }

}
