package de.htwg.se.pokelite
package controller

import util.{Observable, UndoManager}
import model.{Command, GameInterface, NothingToRedo, State}
import model.states.*

import scala.util.{Failure, Success}

trait ControllerInterface() extends Observable :
  val undoManager: UndoManager
  var game:GameInterface

  def moveDone(newGame:GameInterface, command:Command): Unit

  def move(command:Option[Command]): Unit

  def undoMove(): Unit

  def redoMove(): Unit

  def initPlayers():Unit
  def addPlayer(name: String):Unit
  def addPokemons(list: String): Unit
  def nextMove(input:String): Unit
  def attackWith(input: String): Unit
  def selectPokemon(input: String): Unit
  def restartTheGame(): Unit

  



