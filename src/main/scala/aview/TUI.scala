package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{ Attack, AttackMove, ControlMove, Move, PlayerMove, PokeMove, Pokemon, PokemonType }
import util.Observer

import de.htwg.se.pokelite.model.PokemonType.{ Brutalanda, Glurak, Simsala }

import scala.io.StdIn.readLine


class TUI(controller : Controller) extends Observer :
  controller.add( this )

  override def update : Unit = println( controller.field.toString )

  def run() : Unit =
    println( controller.field.toString )
    getInput()
    choosePokemon()
    choosePokemon()
    inputLoop()


  def inputLoop() : Unit =
    chooseAttack()
    inputLoop()

  def getInput() : Unit =
    print( "Enter name of Player 1: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )
    print( "Enter name of Player 2: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )


  def choosePokemon() : Unit =
    if ( controller.field.player1.pokemon.isEmpty ) print( controller.field.player1.name )
    else print( controller.field.player2.name )
    println( " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" )

    val input = readLine()
    val chars = input.toCharArray
    chars( 0 ) match
      case '1' => controller.doAndPublish( controller.put, PokeMove( Pokemon( Glurak ) ) )
      case '2' => controller.doAndPublish( controller.put, PokeMove( Pokemon( Simsala ) ) )
      case '3' => controller.doAndPublish( controller.put, PokeMove( Pokemon( Brutalanda ) ) )
      case _ => choosePokemon()
    controller.onlyDo( controller.put, ControlMove() )

  def chooseAttack() : Unit =
    println( controller.field.toString )
    if ( controller.field.isControlledBy == 1 ) println( controller.field.player1.name + " du bist dran!!!" )
    else println( controller.field.player2.name + " du bist dran!!!" )
    println( "Choose your Attack 1, 2, 3, 4" )

    val input = readLine()
    val char = input.toCharArray
    char( 0 ) match
      case '1' => controller.doAndPublish( controller.put, AttackMove( 0 ) )
      case '2' => controller.doAndPublish( controller.put, AttackMove( 1 ) )
      case '3' => controller.doAndPublish( controller.put, AttackMove( 2 ) )
      case '4' => controller.doAndPublish( controller.put, AttackMove( 3 ) )
      case _ => chooseAttack()
    controller.onlyDo( controller.put, ControlMove() )





