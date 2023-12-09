package de.htwg.se.pokelite
package controller

import model.states.*
import model.{Command, GameInterface, NothingToRedo, State}
import util.{Observable, UndoManager}

import scala.swing.Publisher
import scala.util.{Failure, Success}

trait ControllerInterface extends Observable with Publisher:
  val undoManager : UndoManager
  var game : GameInterface

  def moveDone( newGame : GameInterface, command : Command ) : Unit

  def move( command : Option[ Command ] ) : Unit

  def undoMove( ) : Unit

  def redoMove( ) : Unit

  def initPlayers( ) : Unit

  def addPlayer( name : String ) : Unit

  def addPokemons( list : String ) : Unit

  def nextMove( input : String ) : Unit

  def attackWith( input : String ) : Unit

  def selectPokemon( input : String ) : Unit

  def restartTheGame( ) : Unit

  def save : Unit

  def load : Unit

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

  



