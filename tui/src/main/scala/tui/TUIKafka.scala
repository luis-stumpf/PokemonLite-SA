package tui

import controller.ControllerInterface
import model.State.*
import util.*

import scala.io.StdIn.readLine
import scala.util.{ Failure, Success, Try }
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.Properties
import org.apache.kafka.clients.producer.KafkaProducer
import akka.actor.typed.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.OverflowStrategy
import akka.stream.javadsl.Keep
import akka.stream.scaladsl.Flow
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.concurrent.ExecutionContext
import akka.kafka.scaladsl.Producer
import org.apache.kafka.common.serialization.StringSerializer
import akka.kafka.ProducerSettings
import play.api.libs.json.Json
import org.apache.kafka.clients.producer.ProducerRecord
import akka.kafka.ConsumerSettings
import org.apache.kafka.common.serialization.StringDeserializer
import akka.kafka.scaladsl.Consumer
import akka.kafka.Subscriptions
import model.impl.game.Game
import play.api.libs.json.JsValue

class TUIKafka():
  var game = new Game()

  implicit val system: ActorSystem[?] =
    ActorSystem( Behaviors.empty, "SprayExample" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val producerSettings =
    ProducerSettings( system, new StringSerializer, new StringSerializer )
      .withBootstrapServers( "localhost:9092" )

  val kafkaSink = Producer.plainSink( producerSettings )

  val flow = Flow[String].map { input =>
    if input.isEmpty then Json.obj( "msg" -> "Error: No input detected." )
    else if input.charAt( 0 ) == 'y' then Json.obj( "msg" -> "undo" )
    else if input.charAt( 0 ) == 'z' then Json.obj( "msg" -> "redo" )
    else if input == "save" then Json.obj( "msg" -> "save" )
    else if input == "load" then Json.obj( "msg" -> "load" )
    else if input == "get" then Json.obj( "msg" -> "get" )
    else if input == "update" then Json.obj( "msg" -> "update" )
    else if input == "delete" then Json.obj( "msg" -> "delete" )
    else
      game.state match
        case InitState => Json.obj( "msg" -> "initPlayers" )
        case InitPlayerState =>
          Json.obj( "msg" -> "addPlayer", "data" -> input )
        case InitPlayerPokemonState =>
          Json.obj( "msg" -> "addPokemons", "data" -> input )
        case DesicionState => Json.obj( "msg" -> "nextMove", "data" -> input )
        case FightingState =>
          Json.obj( "msg" -> "attackWith", "data" -> input )
        case SwitchPokemonState =>
          Json.obj( "msg" -> "selectPokemon", "data" -> input )
        case GameOverState => Json.obj( "msg" -> "restartTheGame" )
  }

  def processInputLine( input: String ): Unit = {
    Source
      .single( input )
      .via( flow )
      .map( result =>
        new ProducerRecord[String, String](
          "tui-topic",
          Json.stringify( result )
        )
      )
      .runWith( kafkaSink )
  }

  val consumerSettings =
    ConsumerSettings( system, new StringDeserializer, new StringDeserializer )
      .withBootstrapServers( "localhost:9092" )
      .withGroupId( "group1" )

  val source = Consumer.plainSource(
    consumerSettings,
    Subscriptions.topics( "tui-response-topic" )
  )

  source.runForeach { record =>
    val input = Json.parse( record.value() )

    val msg: String = ( input \ "msg" ).as[String]

    msg match
      case "success" =>
        game = Game.fromJson( ( input \ "data" ).as[JsValue] )
        update( msg )

  }

  def update( message: String ): Unit = {
    println(
      if message == "success" then MatrixField( width = 120, height = 15, game )
      else message
    )

    game.state match
      case InitState              => println( welcomeStatement )
      case InitPlayerState        => println( nameInputRequest )
      case InitPlayerPokemonState => println( availablePokemon )
      case DesicionState          => println( selectionRequest )
      case FightingState          => println( availableAttackOptions )
      case SwitchPokemonState     => println( thePlayersPokemon )
      case GameOverState          => println( aGameOverMessage )
  }

  def welcomeStatement: String =
    "PokemonLite has loaded, type anything to begin."

  def nameInputRequest: String =
    "Enter name of Player " + game.turn + ": "

  def getCurrentPlayerName: String =
    if game.turn == 1 then game.player1.map( _.name ).getOrElse( "" )
    else game.player2.map( _.name ).getOrElse( "" )

  def availablePokemon: String =
    "Choose your Pokemon " + getCurrentPlayerName + ": \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n"

  def getCurrentPlayerPokemon: String =
    if game.turn == 1 then getPlayer1Pokemon else getPlayer2Pokemon

  def getPlayer1Pokemon: String = game.player1.get.pokemons.contents
    .map( p => p.get )
    .mkString( "   " )

  def getPlayer2Pokemon: String = game.player2.get.pokemons.contents
    .map( p => p.get )
    .mkString( "   " )

  def thePlayersPokemon: String =
    "Your current Pokemon are: " + getCurrentPlayerPokemon

  def selectionRequest: String =
    "These are all possible decisions: 1: Attack, 2: Switch Pokemon"

  def availableAttackOptions: String = "Your possible Attacks are: 1, 2, 3, 4"

  def aGameOverMessage: String =
    "GameOver, " + game.winner.get.name + " has won the Game!\n" +
      "Type anything to play again."
