package de.htwg.se.pokelite
package controller

import util.UndoManager

import de.htwg.se.pokelite.model.*

import scala.util.{ Failure, Success }

case class Controller() extends Observable :
  var game = Game()
  val undoManager = new UndoManager


  def moveDone(newGame:Game, command:Command): Unit = {
    game = newGame
    undoManager.doStep(game, command)
    notifyObservers
  }

  def move(command:Option[Command]): Unit = {
    command.get.doStep(game) match {
      case Success( game ) => moveDone( game, command.get )
      case Failure( NoCommandFound ) =>
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

  



