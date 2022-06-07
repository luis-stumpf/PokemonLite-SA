package de.htwg.se.pokelite.controller.impl

import de.htwg.se.pokelite.controller.ControllerInterface
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.InitPlayerState
import de.htwg.se.pokelite.model.{ Command, GameInterface }
import de.htwg.se.pokelite.util.UndoManager

import scala.util.{ Failure, Success }

case class Controller() extends ControllerInterface() :
  val undoManager = new UndoManager
  var game : GameInterface = Game()

  def moveDone(newGame : GameInterface, command : Command) : Unit = {
    game = newGame
    undoManager.doStep( game, command )
    notifyObservers()
  }

  def move(command : Option[ Command ]) : Unit = {
    command.get.doStep( this.game ) match {
      case Success( game ) =>
        moveDone( game, command.get )
      case Failure( x ) =>
        notifyObservers( x.toString )
    }
  }

  def undoMove() : Unit = {
    undoManager.undoStep() match
      case Success( command ) =>
        game = command.undoStep( this.game )
        notifyObservers()
      case Failure( x ) => notifyObservers( x.toString )

  }

  def redoMove() : Unit = {
    undoManager.redoStep() match
      case Success( command ) =>
        game = command.doStep( game ).get
        notifyObservers()
      case Failure( x ) => notifyObservers( x.toString )
  }

  def initPlayers() : Unit = move( game.state.initPlayers() )

  def addPlayer(name : String) : Unit = move( game.state.addPlayer( name ) )

  def addPokemons(list : String) : Unit = move( game.state.addPokemons( list ) )

  def nextMove(input : String) : Unit = move( game.state.nextMove( input ) )

  def attackWith(input : String) : Unit = move( game.state.attackWith( input ) )

  def selectPokemon(input : String) : Unit = move( game.state.switchPokemonTo( input ) )

  def restartTheGame() : Unit =
    move( game.state.restartTheGame())
    game = Game()
