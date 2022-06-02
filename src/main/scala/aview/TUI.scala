package de.htwg.se.pokelite
package aview

import model.*
import util.*
import controller.impl.Controller

import de.htwg.se.pokelite.model.states.*

import scala.io.StdIn.readLine
import scala.util.{ Failure, Success, Try }


class TUI(controller : Controller) extends Observer :

  controller.add( this )

  def processInputLine(input : String) : Unit = {
    if input.charAt(0) == 'y' then controller.undoMove()
    else if input.charAt(0) == 'z' then controller.redoMove()
    else
      controller.game.gameState match
        case InitState() =>  controller.initPlayers()
        case InitPlayerState() => controller.addPlayer(input)
        case InitPlayerPokemonState() => controller.addPokemons(input)
        case DesicionState() => controller.nextMove(input)
        case FightingState() => controller.attackWith(input)
        case SwitchPokemonState() => controller.selectPokemon(input)
        case GameOverState() => theGameIsOver()
  }

  override def update : Unit = {
    println(controller.game.toString)
    controller.game.gameState match
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
    
  def readPlayerName() : Unit =
    println( "Enter name of Player "+controller.game.gameTurn+": " )

  def getCurrentPlayerName() : String =
    if controller.game.gameTurn == 1 then controller.game.gamePlayer1.get.getName
    else controller.game.gamePlayer2.get.getName

  def readPokemons(): Unit =

    println("Choose your Pokemon "+getCurrentPlayerName()+": \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )

  def getCurrentPlayerPokemons(): String =
    if controller.game.gameTurn == 1 then controller.game.gamePlayer1.get.getPokemons.contents.map(p=> p.get).mkString("   ")
    else controller.game.gamePlayer2.get.getPokemons.contents.map(p=>p.get).mkString("   ")

  def choosePokemon(): Unit =

    println("Your current Pokemon are: "+getCurrentPlayerPokemons())



  def readNextMove(): Unit =
    println("These are all possible desicions: 1: Attack, 2: Switch Pokemon" )


  def readAttack(): Unit =
    println("You Possible Attacks are: 1, 2, 3, 4")


  def theGameIsOver(): Unit =
    println("GameOver, " + controller.game.gameWinner.get.getName + " has won the Game!")

