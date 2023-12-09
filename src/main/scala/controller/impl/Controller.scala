package de.htwg.se.pokelite.controller.impl

import com.google.inject.{CreationException, Guice, Inject}
import de.htwg.se.pokelite.PokemonLiteModule
import de.htwg.se.pokelite.controller.{AttackEvent, ControllerInterface, GameOver, PlayerChanged, PokemonChanged, StateChanged, UnknownCommand}
import de.htwg.se.pokelite.model.commands.GameOverCommand
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.InitPlayerState
import de.htwg.se.pokelite.model.{Command, FileIOInterface, GameInterface, NoSaveGameFound, NotAbleToSave}
import de.htwg.se.pokelite.util.UndoManager

import scala.util.{Failure, Success}
import scala.swing.Publisher

case class Controller @Inject() () extends ControllerInterface:
  val undoManager = new UndoManager
  var game : GameInterface = Game()

  def moveDone( newGame : GameInterface, command : Command ) : Unit = {
    game = newGame
    undoManager.doStep( command )
    notifyObservers()
    command.getClass.getSimpleName match {
        case "ChangeStateCommand" => publish(new StateChanged)
        case "AddPokemonCommand" => publish(new PlayerChanged)
        case "AddPlayerCommand" => publish(new PlayerChanged)
        case "GameOverCommand" => publish(new GameOver)
        case "SelectNextMoveCommand" => publish(new StateChanged)
        case "SwitchPokemonCommand" => publish(new PokemonChanged)
        case "AttackCommand" => publish(new AttackEvent)
        case _ => publish(new UnknownCommand())
    }
  }

  def move( command : Option[ Command ] ) : Unit = {
    command.get.doStep( this.game ) match {
      case Success( game ) =>
        moveDone( game, command.get )
      case Failure( x ) =>
        notifyObservers( x.toString )
    }
  }

  def undoMove( ) : Unit = {
    undoManager.undoStep() match
      case Success( command ) =>
        game = command.undoStep( this.game )
        notifyObservers()
      case Failure( x ) => notifyObservers( x.toString )

  }

  def redoMove( ) : Unit = {
    undoManager.redoStep() match
      case Success( command ) =>
        game = command.doStep( game ).get
        notifyObservers()
      case Failure( x ) => notifyObservers( x.toString )
  }

  def initPlayers( ) : Unit = move( game.state.initPlayers() )

  def addPlayer( name : String ) : Unit = move( game.state.addPlayer( name ) )

  def addPokemons( list : String ) : Unit = move( game.state.addPokemons( list ) )

  def nextMove( input : String ) : Unit = move( game.state.nextMove( input ) )

  def attackWith( input : String ) : Unit = move( game.state.attackWith( input ) )

  def selectPokemon( input : String ) : Unit = move( game.state.switchPokemonTo( input ) )

  def restartTheGame( ) : Unit =

    move( game.state.restartTheGame( this.game ) )

  def save : Unit = {

    try{
      Guice.createInjector( new PokemonLiteModule ).getInstance( classOf[ FileIOInterface ] ).save(game)
      notifyObservers()
    } catch {
      case e: Exception => notifyObservers(NotAbleToSave.toString)
    }
  }

  def load : Unit = {
    try{
    game = Guice.createInjector( new PokemonLiteModule ).getInstance( classOf[ FileIOInterface ] ).load
    notifyObservers()
    } catch {
      case e: Exception => notifyObservers(NoSaveGameFound.toString)
    }
  }
