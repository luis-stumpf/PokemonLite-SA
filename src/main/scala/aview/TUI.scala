package de.htwg.se.pokelite
package aview

import controller.*
import model.PokemonType.*
import model.*
import util.*

import de.htwg.se.pokelite.model.states.InitPlayerPokemonState

import java.util
import scala.io.StdIn.readLine
import scala.util.{ Failure, Success, Try }


class TUI(controller : Controller) extends Observer :

  controller.add( this )

  override def update : Unit =
    controller.game.state match
      case InitState() => println("PokemonLite has begun")
      case InitPlayerState() => readPlayerName()
      case InitPlayerPokemonState() => readPokemon()
      case FightingState() => readAttack()
      case GameOverState() => theGameIsOver()


  def readPlayerName() : Unit =
    print( "Enter name: " )
    controller.addPlayer(readLine())

  def readPokemons(): Unit =
    println(" Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )
    controller.addPokemons(readLine())

  def readAttack(): Unit =
    println(controller.game.toString)
    println("choose your Attack 1, 2, 3, 4" )
    contoller.attackWith(readLine())

  def theGameIsOver(): Unit =
    println("GameOver, " + controller.game.winner.get.name + " has won the Game!")

