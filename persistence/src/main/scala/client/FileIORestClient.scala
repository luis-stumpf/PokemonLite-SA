package client

import model.GameInterface
import util.UndoManager
import scala.util.Try

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import scala.concurrent.Future
import scala.util.{ Failure, Success }
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.impl.game.Game
import play.api.libs.json.Json
import scalafx.scene.input.KeyCode.F
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import fileIo.FileIOInterface
import play.api.libs.json.JsValue

class FileIORestClient extends FileIOInterface {

  override def save( gameJson: JsValue ): Unit = ???

  override def load: GameInterface = makeGetRequest( "load" )

  override def save( gameSave: GameInterface ): Unit = {
    makePostRequest( "save", gameSave )
  }

  private val persistenceServiceUrl = "http://localhost:4002"
  override def delete: Unit = {
    makePutRequest( "delete", None )
  }

  override def update( gameUpdate: GameInterface ): Unit =
    makePutRequest( "update", Some( gameUpdate ) )

  def makePostRequest( command: String, game: GameInterface ): Unit = {

    implicit val system = ActorSystem( Behaviors.empty, "SingleRequest" )
    implicit val executionContext = system.executionContext

    val responseFuture = Http()
      .singleRequest(
        HttpRequest(
          method = HttpMethods.POST,
          uri = s"$persistenceServiceUrl/save",
          entity = HttpEntity(
            ContentTypes.`application/json`,
            game.toJson.toString()
          )
        )
      )
      .onComplete( {
        case Success( response ) =>
        case Failure( exception ) =>
          println( exception )
      } )

  }

  def makeGetRequest( command: String ): GameInterface = {
    implicit val system = ActorSystem( Behaviors.empty, "SingleRequest" )
    implicit val executionContext = system.executionContext

    val responseFuture = Http().singleRequest(
      HttpRequest( uri = s"$persistenceServiceUrl/load" )
    )

    val responseJsonFuture = responseFuture.flatMap { response =>
      Unmarshal( response.entity ).to[String].map { jsonString =>
        Json.parse( jsonString )
      }
    }

    val responseJson = Await.result( responseJsonFuture, 5.seconds )
    Game.fromJson( responseJson )
  }

  def makePutRequest( command: String, game: Option[GameInterface] ): Unit = {
    implicit val system = ActorSystem( Behaviors.empty, "SingleRequest" )
    implicit val executionContext = system.executionContext

    val responseFuture = Http()
      .singleRequest(
        HttpRequest(
          method = HttpMethods.PUT,
          uri = s"$persistenceServiceUrl/$command",
          entity = HttpEntity(
            ContentTypes.`application/json`,
            game match {
              case Some( g ) => g.toJson.toString()
              case None      => ""
            }
          )
        )
      )
      .onComplete( {
        case Success( response ) => println( response )
        case Failure( exception ) =>
          println( exception )
      } )
  }
}
