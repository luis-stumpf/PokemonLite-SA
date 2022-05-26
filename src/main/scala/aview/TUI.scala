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
      case InitState() =>  controller.initPlayers()
      case InitPlayerState() => controller.addPlayer(input)
      case InitPlayerPokemonState() => controller.addPokemons(input)
      case DesicionState() => controller.nextMove(input)
      case FightingState() => controller.attackWith(input)
      case SwitchPokemonState() => controller.selectPokemon(input)
      case GameOverState() => theGameIsOver()
  }

  override def update : Unit = {
    controller.game.state match
      case InitState() => initialState()
      case InitPlayerState() => readPlayerName(  )
      case InitPlayerPokemonState() => readPokemons(  )
      case DesicionState() => readNextMove(  )
      case FightingState() => readAttack(  )
      case SwitchPokemonState() => choosePokemon(  )
      case GameOverState() => theGameIsOver()
  }



  def initialState(): Unit =
    println("PokemonLit, type anyting to behin")
    controller.initPlayers()
    
  def readPlayerName() : Unit =
    print( "Enter name: " )


  def readPokemons(): Unit =
    println("Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )


  def choosePokemon(): Unit =
    println("your available pokemon are.... choose 1,2 or 3")



  def readNextMove(): Unit =
    println(controller.game.toString)
    println("These are all possible desicions: 1: Attack, 2: Switch Pokemon" )


  def readAttack(): Unit =
    println("You Possible Attacks are: 1, 2, 3, 4")


  def theGameIsOver(): Unit =
    println("GameOver, " + controller.game.winner.get.name + " has won the Game!")

