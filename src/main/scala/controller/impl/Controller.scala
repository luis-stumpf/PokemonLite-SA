package de.htwg.se.pokelite.controller.impl

import de.htwg.se.pokelite.controller.ControllerInterface
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.InitPlayerState
import de.htwg.se.pokelite.model.{Command, GameInterface}
import de.htwg.se.pokelite.util.UndoManager

import scala.util.{Failure, Success}

case class Controller() extends ControllerInterface() :
  val undoManager = new UndoManager
  var gameVal:Game = Game()
  var game:GameInterface = gameVal

  def moveDone(newGame:GameInterface, command:Command): Unit = {
    game = newGame.setNextTurn()
    undoManager.doStep(game, command)
    notifyObservers
  }

  def move(command:Option[Command]): Unit = {
    command.get.doStep(this.game) match {
      case Success( game ) =>
        moveDone( game, command.get )
      case Failure( x ) => x.getMessage
    }
  }

  def undoMove(): Unit = {
    undoManager.undoStep() match
      case Success(command) =>
        game = command.undoStep(this.game)
        notifyObservers
      case Failure(x) => x.getMessage

  }

  def redoMove(): Unit = {
    undoManager.redoStep() match
      case Success(command) =>
        game = command.doStep(game).get
        notifyObservers
      case Failure(x) => x.getMessage
  }

  def initPlayers():Unit = move ( game.gameState.initPlayers() )
  def addPlayer(name: String):Unit = move ( game.gameState.addPlayer(name))
  def addPokemons(list: String): Unit = move ( game.gameState.addPokemons(list))
  def nextMove(input:String): Unit = move( game.gameState.nextMove(input))
  def attackWith(input: String): Unit = move(game.gameState.attackWith(input))
  def selectPokemon(input: String): Unit = move(game.gameState.switchPokemonTo(input))
