package de.htwg.se.pokelite
package controller

import model.states.*
import model.{ Command, GameInterface, NothingToRedo, State }
import util.{ Observable, UndoManager }

import scala.swing.Publisher
import scala.util.{ Failure, Success }
import org.checkerframework.checker.nullness.Opt

trait ControllerInterface extends Observable with Publisher:
  val undoManager: UndoManager
  var game: GameInterface

  def doAndPublish( command: Option[Command] ): Unit

  def undoMove(): Unit

  def redoMove(): Unit

  def initPlayers(): Option[Command]

  def addPlayer( name: String ): Option[Command]

  def addPokemons( list: String ): Option[Command]

  def nextMove( input: String ): Option[Command]

  def attackWith( input: String ): Option[Command]

  def selectPokemon( input: String ): Option[Command]

  def restartTheGame(): Option[Command]

  def save: Unit

  def load: Unit

import scala.swing.event.Event

class PlayerChanged extends Event

class StateChanged extends Event

class AttackEvent extends Event

class PokemonChanged extends Event

class GameOver extends Event

class GameSaved extends Event

class GameLoaded extends Event

class UnknownCommand extends Event

class PokemonLiteShutdown extends Event
