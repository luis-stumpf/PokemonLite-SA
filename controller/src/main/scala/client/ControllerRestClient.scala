package client

import controller.ControllerInterface
import fileIo.FileIOInterface
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

class ControllerRestClient( using val fileIo: FileIOInterface )
    extends ControllerInterface {

  private val controllerServiceUrl = "http://0.0.0.0:4001/controller"
  var game: GameInterface = Game()

  val undoManager: UndoManager[GameInterface] = new UndoManager[GameInterface]

  def makePostRequest( command: String, input: String ): Try[GameInterface] = {
    implicit val system = ActorSystem( Behaviors.empty, "SingleRequest" )
    implicit val executionContext = system.executionContext

    val responseFuture = Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        uri = s"$controllerServiceUrl/$command?input=$input"
      )
    )

    val responseJsonFuture = responseFuture.flatMap { response =>
      Unmarshal( response.entity ).to[String].map { jsonString =>
        Json.parse( jsonString )
      }
    }

    val responseJson = Await.result( responseJsonFuture, 3.seconds )
    Success( Game.fromJson( responseJson ) )
  }

  def makeGetRequest( command: String, input: String ): Try[GameInterface] = {
    implicit val system = ActorSystem( Behaviors.empty, "SingleRequest" )
    implicit val executionContext = system.executionContext

    val responseFuture = Http().singleRequest(
      HttpRequest( uri = s"$controllerServiceUrl/$command" )
    )

    val responseJsonFuture = responseFuture.flatMap { response =>
      Unmarshal( response.entity ).to[String].map { jsonString =>
        Json.parse( jsonString )
      }
    }

    val responseJson = Await.result( responseJsonFuture, 3.seconds )
    Success( Game.fromJson( responseJson ) )
  }

  def getGame(): Try[GameInterface] =
    makeGetRequest( "game", "" )

  override def doAndPublish( doThis: () => Try[GameInterface] ) =
    game = doThis() match {
      case Success( newGame ) => newGame
      case Failure( x ) =>
        notifyObservers( x.toString() )
        game
    }
    notifyObservers()

  override def doAndPublish(
    doThis: String => Try[GameInterface],
    command: String
  ) =
    game = doThis( command ) match {
      case Success( newGame ) => newGame
      case Failure( x ) =>
        notifyObservers( x.toString() )
        game
    }
    notifyObservers()

  override def redoMove(): Try[GameInterface] =
    makePostRequest( "redo", "" )
  override def load(): Try[GameInterface] = makePostRequest( "load", "" )

  override def selectPokemon( input: String ): Try[GameInterface] =
    makePostRequest( "switchPokemon", input )

  override def nextMove( input: String ): Try[GameInterface] =
    makePostRequest( "next", input )

  override def save(): Try[GameInterface] = makePostRequest( "save", "" )

  override def restartTheGame(): Try[GameInterface] =
    makePostRequest( "gameOver", "" )

  override def addPokemons( list: String ): Try[GameInterface] =
    makePostRequest( "addPokemons", list )

  override def addPlayer( name: String ): Try[GameInterface] =
    makePostRequest( "addPlayer", name )

  override def attackWith( input: String ): Try[GameInterface] =
    makePostRequest( "attack", input )

  override def undoMove(): Try[GameInterface] =
    makePostRequest( "undo", "" )

  override def initPlayers(): Try[GameInterface] =
    makePostRequest( "initPlayers", "" )

  override def updateGame(): Try[GameInterface] =
    makePostRequest( "update", game.toJson.toString() )

  override def deleteGame(): Try[GameInterface] =
    makePostRequest( "delete", "" )

}
