package controller

import model.State.*
import model.{ GameInterface, State }
import util.{ Observable, UndoManager }

import scala.swing.Publisher
import scala.util.{ Failure, Success, Try }
import org.checkerframework.checker.nullness.Opt
import scala.swing.event.Event

trait ControllerInterface extends Observable with Publisher:
  val undoManager: UndoManager[GameInterface]
  var game: GameInterface

  def doAndPublish(
    doThis: String => Try[GameInterface],
    command: String
  ): Unit

  def doAndPublish( doThis: () => Try[GameInterface] ): Unit

  def undoMove(): Try[GameInterface]

  def redoMove(): Try[GameInterface]

  def initPlayers(): Try[GameInterface]

  def addPlayer( name: String ): Try[GameInterface]

  def addPokemons( list: String ): Try[GameInterface]

  def nextMove( input: String ): Try[GameInterface]

  def attackWith( input: String ): Try[GameInterface]

  def selectPokemon( input: String ): Try[GameInterface]

  def restartTheGame(): Try[GameInterface]

  def save(): Try[GameInterface]

  def load(): Try[GameInterface]

class PlayerChanged extends Event

class StateChanged extends Event

class AttackEvent extends Event

class PokemonChanged extends Event

class GameOver extends Event

class UnknownCommand extends Event
