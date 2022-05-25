package de.htwg.se.pokelite
package controller

import util.UndoManager
import model.Game

import scala.util.{ Failure, Success }

case class Controller() extends Observable, Stateable, Event :
  var game = Game()
  val undoManager = new UndoManager


  def moveDone(newGame:Game, command:Command): Unit = {
    game = newGame
    undoManager.doStep(game, command)
    notifyAll()
  }

  def move(command:Option[Command]): Unit = {
    command.get.doStep(game) match {
      case Success( game ) => moveDone( game, command.get )
      case Failure( ) =>
    }
  }

  def undoMove(): Unit = {
    command: Command = undoManager.undoStep()
  }

  def redoMove(): Unit = {
    undoManager.redoStep().get.doStep()
    doAndPublish(undoManager.redoStep(game))
  }

  def initPlayers():Unit = move ( game.state.initPlayers() )
  def addPlayer(name: String):Unit = move ( game.state.addPlayer(name))
  def addPokemons(list: String)Unit = move ( game.sate.Ini)

  



