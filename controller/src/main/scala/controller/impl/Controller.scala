package controller.impl

import com.google.inject.{ CreationException, Guice, Inject }
import controller.{
  AttackEvent,
  ControllerInterface,
  GameOver,
  PlayerChanged,
  PokemonChanged,
  StateChanged,
  UnknownCommand
}

import model.impl.game.Game
import model.State.*
import model.GameInterface

import fileIo.FileIOInterface
import util.UndoManager
import util.Command
import util.Observer

import scala.util.{ Failure, Success, Try }
import scala.swing.Publisher
import controller.commands.{
  AddPokemonCommand,
  LoadCommand,
  AddPlayerCommand,
  ChangeStateCommand,
  AttackCommand,
  SelectNextMoveCommand,
  SwitchPokemonCommand,
  SaveCommand,
  GameOverCommand
}

case class Controller @Inject() () extends ControllerInterface:
  val undoManager = new UndoManager[GameInterface]
  var game: GameInterface = Game()

  private val fileIO = new fileIo.json.FileIO

  /*Guice
    .createInjector( new PokemonLiteModule )
    .getInstance( classOf[FileIOInterface] )
   */

  def handleRequest(
    doThis: String => Try[GameInterface],
    input: String
  ): Try[GameInterface] = {
    doThis( input ) match {
      case Success( newGame ) =>
        game = newGame
        Success( newGame )
      case Failure( x ) =>
        Failure( x )
    }
  }

  def handleRequest( doThis: () => Try[GameInterface] ): Try[GameInterface] = {
    doThis() match
      case Success( newGame ) =>
        game = newGame
        Success( newGame )
      case Failure( x ) =>
        Failure( x )

  }

  def doAndPublish( doThis: String => Try[GameInterface], input: String ) = {
    game = doThis( input ) match {
      case Success( newGame ) =>
        newGame
      case Failure( x ) =>
        notifyObservers( x.toString )
        game
    }
    //  usePublisher( undoManager.lastCommand )
    notifyObservers()
  }

  def doAndPublish( doThis: () => Try[GameInterface] ) = {
    game = doThis() match {
      case Success( newGame ) =>
        newGame
      case Failure( x ) =>
        notifyObservers( x.toString )
        game
    }
    // usePublisher( undoManager.lastCommand )
    notifyObservers()
  }

  def undoMove(): Try[GameInterface] = undoManager.undoStep( game )

  def redoMove(): Try[GameInterface] = undoManager.redoStep( game )

  def initPlayers(): Try[GameInterface] = undoManager.doStep(
    game,
    ChangeStateCommand( game.state, InitPlayerState )
  )

  def addPlayer( name: String ): Try[GameInterface] =
    undoManager.doStep( game, AddPlayerCommand( name, game.state ) )

  def addPokemons( list: String ): Try[GameInterface] =
    undoManager.doStep( game, AddPokemonCommand( list, game.state ) )

  def addPokemons( list: List[Int] ): Try[GameInterface] =
    undoManager.doStep(
      game,
      AddPokemonCommand( list.mkString( "" ), game.state )
    )

  def nextMove( input: String ): Try[GameInterface] =
    undoManager.doStep( game, SelectNextMoveCommand( input, game.state ) )

  def attackWith( input: String ): Try[GameInterface] =
    undoManager.doStep( game, AttackCommand( input, game.state ) )

  def selectPokemon( input: String ): Try[GameInterface] =
    undoManager.doStep( game, SwitchPokemonCommand( input, game.state ) )

  def restartTheGame(): Try[GameInterface] =
    undoManager.doStep( game, GameOverCommand( game, game.state ) )

  private def usePublisher( command: Option[Command[GameInterface]] ) = {
    command match
      case None =>
        notifyObservers( "Unknown Command" ); publish( new UnknownCommand )
      case Some( c ) =>
        c.getClass.getSimpleName match {
          case "ChangeStateCommand"    => publish( new StateChanged )
          case "AddPokemonCommand"     => publish( new PlayerChanged )
          case "AddPlayerCommand"      => publish( new PlayerChanged )
          case "GameOverCommand"       => publish( new GameOver )
          case "SelectNextMoveCommand" => publish( new StateChanged )
          case "SwitchPokemonCommand"  => publish( new PokemonChanged )
          case "AttackCommand"         => publish( new AttackEvent )
          case _                       => publish( new UnknownCommand )
        }
  }

  def save(): Try[GameInterface] =
    undoManager.doStep( game, SaveCommand( fileIO ) )

  def load(): Try[GameInterface] =
    undoManager.doStep( game, LoadCommand( fileIO ) )
