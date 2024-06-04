package controller

import model.State.*
import model.{ GameInterface, State }
import util.{ Observable, UndoManager }

import scala.util.{ Failure, Success, Try }
import org.checkerframework.checker.nullness.Opt

trait ControllerInterface extends Observable:
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

  def getGame(): Try[GameInterface]

  def updateGame(): Try[GameInterface]

  def deleteGame(): Try[GameInterface]
