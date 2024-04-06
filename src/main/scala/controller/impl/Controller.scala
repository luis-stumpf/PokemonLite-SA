package de.htwg.se.pokelite.controller.impl

import com.google.inject.{ CreationException, Guice, Inject }
import de.htwg.se.pokelite.PokemonLiteModule
import de.htwg.se.pokelite.controller.{
  AttackEvent,
  ControllerInterface,
  GameOver,
  PlayerChanged,
  PokemonChanged,
  StateChanged,
  UnknownCommand
}
import de.htwg.se.pokelite.model.commands.GameOverCommand
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.InitPlayerState
import de.htwg.se.pokelite.model.{
  Command,
  FileIOInterface,
  GameInterface,
  NoSaveGameFound,
  NotAbleToSave
}
import de.htwg.se.pokelite.util.UndoManager

import scala.util.{ Failure, Success }
import scala.swing.Publisher
import de.htwg.se.pokelite.PokemonLite.controller

case class Controller @Inject() () extends ControllerInterface:
  val undoManager = new UndoManager
  var game: GameInterface = Game()

  private val fileIO = Guice
    .createInjector( new PokemonLiteModule )
    .getInstance( classOf[FileIOInterface] )

  def doAndPublish( command: Option[Command] ): Unit = {

    command match
      case None => notifyObservers( "Unknown Command" )
      case Some( c ) =>
        c.doStep( this.game ) match {
          case Success( newGame ) =>
            game = newGame
            undoManager.doStep( c )
            notifyObservers()
            c.getClass.getSimpleName match {
              case "ChangeStateCommand"    => publish( new StateChanged )
              case "AddPokemonCommand"     => publish( new PlayerChanged )
              case "AddPlayerCommand"      => publish( new PlayerChanged )
              case "GameOverCommand"       => publish( new GameOver )
              case "SelectNextMoveCommand" => publish( new StateChanged )
              case "SwitchPokemonCommand"  => publish( new PokemonChanged )
              case "AttackCommand"         => publish( new AttackEvent )
              case _                       => publish( new UnknownCommand )
            }
          case Failure( x ) =>
            notifyObservers( x.toString )
        }
  }

  def undoMove(): Unit = {
    undoManager.undoStep() match
      case Success( command ) =>
        game = command.undoStep( this.game )
        notifyObservers()
      case Failure( x ) => notifyObservers( x.toString )

  }

  def redoMove(): Unit = {
    undoManager.redoStep() match
      case Success( command ) =>
        game = command.doStep( game ).get
        notifyObservers()
      case Failure( x ) => notifyObservers( x.toString )
  }

  def initPlayers(): Option[Command] = game.state.initPlayers()

  def addPlayer( name: String ): Option[Command] = game.state.addPlayer( name )

  def addPokemons( list: String ): Option[Command] =
    game.state.addPokemons( list )

  def nextMove( input: String ): Option[Command] = game.state.nextMove( input )

  def attackWith( input: String ): Option[Command] =
    game.state.attackWith( input )

  def selectPokemon( input: String ): Option[Command] =
    game.state.switchPokemonTo( input )

  def restartTheGame(): Option[Command] = game.state.restartTheGame( game )

  def save: Unit = {
    fileIO.save( game )
    notifyObservers()
  }

  def load: Unit = {
    fileIO.load
    notifyObservers()
  }
