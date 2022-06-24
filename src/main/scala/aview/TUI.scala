package de.htwg.se.pokelite
package aview

import controller.ControllerInterface
import controller.impl.Controller
import model.*
import model.states.*
import util.*

import scala.io.StdIn.readLine
import scala.util.{ Failure, Success, Try }


class TUI( controller : ControllerInterface ) extends Observer :

  controller.add( this )

  def processInputLine( input : String ) : Unit = {
    if input.isEmpty then
      update( "Error: No input detected." )
    else if input.charAt( 0 ) == 'y' then controller.undoMove()
    else if input.charAt( 0 ) == 'z' then controller.redoMove()
    else if input == "save" then controller.save
    else if input == "load" then controller.load
    else
      controller.game.state match
        case InitState() => controller.initPlayers()
        case InitPlayerState() => controller.addPlayer( input )
        case InitPlayerPokemonState() => controller.addPokemons( input )
        case DesicionState() => controller.nextMove( input )
        case FightingState() => controller.attackWith( input )
        case SwitchPokemonState() => controller.selectPokemon( input )
        case GameOverState() => controller.restartTheGame()
  }

  override def update( message : String ) : Unit = {
    if message == "success" then
      println( controller.game.toString )
    else
      println( message )

    controller.game.state match
      case InitState() => printWelcomeStatement()
      case InitPlayerState() => printNameRequest()
      case InitPlayerPokemonState() => printAvailablePokemon()
      case DesicionState() => printSelectionRequest()
      case FightingState() => printAvailableAttackOptions()
      case SwitchPokemonState() => printThePlayersPokemon()
      case GameOverState() => printThatTheGameIsOver()
  }


  def printWelcomeStatement( ) : Unit =
    println( "PokemonLite, type anything to begin." )

  def printNameRequest( ) : Unit =
    println( "Enter name of Player " + controller.game.turn + ": " )

  def getCurrentPlayerName( ) : String =
    if controller.game.turn == 1 then controller.game.player1.get.name
    else controller.game.player2.get.name

  def printAvailablePokemon( ) : Unit =

    println( "Choose your Pokemon " + getCurrentPlayerName() + ": \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )

  def getCurrentPlayerPokemons( ) : String =
    if controller.game.turn == 1 then controller.game.player1.get.pokemons.contents.map( p => p.get ).mkString( "   " )
    else controller.game.player2.get.pokemons.contents.map( p => p.get ).mkString( "   " )

  def printThePlayersPokemon( ) : Unit =

    println( "Your current Pokemon are: " + getCurrentPlayerPokemons() )


  def printSelectionRequest( ) : Unit =
    println( "These are all possible desicions: 1: Attack, 2: Switch Pokemon" )


  def printAvailableAttackOptions( ) : Unit =
    println( "Your possible Attacks are: 1, 2, 3, 4" )


  def printThatTheGameIsOver( ) : Unit =
    println( "GameOver, " + controller.game.winner.get.name + " has won the Game!" )
    println( "Type anything to play again." )

