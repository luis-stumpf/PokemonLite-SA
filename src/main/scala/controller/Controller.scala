package de.htwg.se.pokelite
package controller

import util.{ Observable, UndoManager }
import model.{Game, State, Command}
import model.states.*

import scala.util.{ Failure, Success }

case class Controller() extends Observable :
  val undoManager = new UndoManager
  var game:Game = Game()

  def copy(game:Game = game): Game = game

  def moveDone(newGame:Game, command:Command): Unit = {
   game = newGame
    //setnexxtturn
    println(this.game.state)
    undoManager.doStep(game, command)
    notifyObservers
  }

  def move(command:Option[Command]): Unit = {
    command.get.doStep(this.game) match {
      case Success( game ) =>
        moveDone( game, command.get )
      case Failure( t ) =>
    }
  }

  /*
  def undoMove(): Unit = {
    command: Command = undoManager.undoStep()
    notifyObservers
  }

  def redoMove(): Unit = {
    undoManager.redoStep().get.doStep()
    doAndPublish(undoManager.redoStep(game))
    notifyObservers
  }
  */

  def initPlayers():Unit = move ( game.state.initPlayers() )
  def addPlayer(name: String):Unit = move ( game.state.addPlayer(name))
  def addPokemons(list: String): Unit = move ( game.state.addPokemons(list))
  def attackWith(input:String): Unit = move( game.state.attackWith(input))

  



