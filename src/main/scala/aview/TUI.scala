package de.htwg.se.pokelite
package aview

import model.*
import util.*
import controller.Controller

import de.htwg.se.pokelite.model.states.*

import scala.io.StdIn.readLine
import scala.util.{ Failure, Success, Try }


class TUI(controller : Controller) extends Observer :

  controller.add( this )

  def processInputLine(input : String) : Unit = {
    controller.game.state match
      case InitState() => initialState( input )
      case InitPlayerState() => readPlayerName( input )
      case InitPlayerPokemonState() => readPokemons( input )
      case DesicionState() => readNextMove( input )
      case FightingState() => readAttack( input )
      case SwitchPokemonState() => choosePokemon( input )
      case GameOverState() => theGameIsOver()
  }

  override def update : Unit = {
    println(controller.game.toString)
  }



  def initialState(input: String): Unit =
    println("PokemonLit, type anyting to behin")
    controller.initPlayers()
    
  def readPlayerName(input: String) : Unit =
    print( "Enter name: " )
    controller.addPlayer(input)

  def readPokemons(input: String): Unit =
    println("Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )
    controller.addPokemons(input)

  def choosePokemon(input: String): Unit =
    println("your available pokemon are.... choose 1,2 or 3")
    controller.selectPokemon(input)


  def readNextMove(input: String): Unit =
    println(controller.game.toString)
    println("These are all possible desicions: 1: Attack, 2: Switch Pokemon" )
    controller.nextMove(input)

  def readAttack(input: String): Unit =
    println("You Possible Attacks are: 1, 2, 3, 4")
    controller.attackWith(input)

  def theGameIsOver(): Unit =
    println("GameOver, " + controller.game.winner.get.name + " has won the Game!")

