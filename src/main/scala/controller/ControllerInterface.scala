package de.htwg.se.pokelite
package controller

import util.{Observable, UndoManager}
import model.{Command, Game, NothingToRedo, State}
import model.states.*

import scala.util.{Failure, Success}

trait ControllerInterface() extends Observable :
  val undoManager: UndoManager
  var game:Game

  def moveDone(newGame:Game, command:Command): Unit

  def move(command:Option[Command]): Unit

  def undoMove(): Unit

  def redoMove(): Unit

  def initPlayers():Unit
  def addPlayer(name: String):Unit
  def addPokemons(list: String): Unit
  def nextMove(input:String): Unit
  def attackWith(input: String): Unit
  def selectPokemon(input: String): Unit

  



