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

    if ( controller.field.player1.pokemons.isEmpty ) print( controller.field.player1.name )
    else print( controller.field.player2.name )


    println( " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" )

    val input = readLine()
    val chars = input.toCharArray
    val pokeList1 = List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) )
    val pokeList2 = List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala ) ) )
    val pokeList3 = List( Some( Pokemon( Simsala ) ), Some( Pokemon( Simsala ) ) )

    chars( 0 ) match
      case '1' => controller.doAndPublish( controller.put, PokeMove( pokeList1 ) )
      case '2' => controller.doAndPublish( controller.put, PokeMove( pokeList2 ) )
      case '3' => controller.doAndPublish( controller.put, PokeMove( pokeList3 ) )
      case _ => choosePokemon()



  def chooseAttack() : Unit =

    object GetName {
      val ofPlayer: String = if (controller.field.isControlledBy == 1) ofPlayer1 else ofPlayer2

      def ofPlayer1: String = controller.field.player1.name
      def ofPlayer2: String = controller.field.player2.name
    }


    println(GetName.ofPlayer + ", choose your Attack 1, 2, 3, 4" )

    val input = readLine()
    val char = input.toCharArray
    char( 0 ) match
      case '1' => controller.doAndPublish( controller.put, AttackMove( 0 ) )
      case '2' => controller.doAndPublish( controller.put, AttackMove( 1 ) )
      case '3' => controller.doAndPublish( controller.put, AttackMove( 2 ) )
      case '4' => controller.doAndPublish( controller.put, AttackMove( 3 ) )









