package controller.impl

import controller.ControllerInterface

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

class Controller( using val fileIO: FileIOInterface )
    extends ControllerInterface:

  val undoManager = new UndoManager[GameInterface]
  var game: GameInterface = Game()

  def doAndPublish(
    doThis: String => Try[GameInterface],
    input: String
  ): Unit = {
    game = doThis( input ) match {
      case Success( newGame ) =>
        newGame
      case Failure( x ) =>
        notifyObservers( x.toString )
        game
    }
    notifyObservers()
  }

  def doAndPublish( doThis: () => Try[GameInterface] ): Unit = {
    game = doThis() match {
      case Success( newGame ) =>
        newGame
      case Failure( x ) =>
        notifyObservers( x.toString )
        game
    }
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

  def nextMove( input: String ): Try[GameInterface] =
    undoManager.doStep( game, SelectNextMoveCommand( input, game.state ) )

  def attackWith( input: String ): Try[GameInterface] =
    undoManager.doStep( game, AttackCommand( input, game.state ) )

  def selectPokemon( input: String ): Try[GameInterface] =
    undoManager.doStep( game, SwitchPokemonCommand( input, game.state ) )

  def restartTheGame(): Try[GameInterface] =
    undoManager.doStep( game, GameOverCommand( game, game.state ) )

  def save(): Try[GameInterface] =
    undoManager.doStep( game, SaveCommand( fileIO ) )

  def load(): Try[GameInterface] =
    undoManager.doStep( game, LoadCommand( fileIO ) )

  def getGame(): Try[GameInterface] = Success( game )
