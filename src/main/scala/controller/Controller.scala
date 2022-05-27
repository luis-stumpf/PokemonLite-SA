package de.htwg.se.pokelite
package controller

import util.{Observable, UndoManager}
import model.{Command, Game, NothingToRedo, State}
import model.states.*

import scala.util.{Failure, Success}

case class Controller() extends Observable :
  val undoManager = new UndoManager
  var game:Game = Game()

  def moveDone(newGame:Game, command:Command): Unit = {
    game = newGame.setNextTurn()
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

  def undoMove(): Unit = {
    game = undoManager.undoStep().get.undoStep(this.game)
    notifyObservers
  }

  def redoMove(): Unit = {
    game = undoManager.redoStep().get.doStep(game).get
    notifyObservers
  }

  def initPlayers():Unit = move ( game.state.initPlayers() )
  def addPlayer(name: String):Unit = move ( game.state.addPlayer(name))
  def addPokemons(list: String): Unit = move ( game.state.addPokemons(list))
  def nextMove(input:String): Unit = move( game.state.nextMove(input))
  def attackWith(input: String): Unit = move(game.state.attackWith(input))
  def selectPokemon(input: String): Unit = move(game.state.switchPokemonTo(input))

  



