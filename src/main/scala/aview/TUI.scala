package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{ Attack, AttackMove, Move, PlayerMove, PokeMove, Pokemon, PokemonType }
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

    println( getName + ", choose your Attack 1, 2, 3, 4" )

    chooseAttack(readLine) match
      case Some(move) => controller.doAndPublish(controller.put, move)
      case None =>
    inputLoop()

  def getInput() : Unit =
    print( "Enter name of Player 1: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )
    print( "Enter name of Player 2: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )

  def getName: String = if ( controller.field.isControlledBy == 1 ) ofPlayer1 else ofPlayer2
  def ofPlayer1 : String = controller.field.player1.name
  def ofPlayer2 : String = controller.field.player2.name

  def choosePokemon() : Unit =

    println( getName +  " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" )

    inputAnalysisPokemon(readLine) match
      case None       =>
      case Some(move) => controller.doAndPublish(controller.put, move)


  def inputAnalysisPokemon(input: String): Option[PokeMove] =
    val chars = input.toCharArray
    val pokeList1 = List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) )
    val pokeList2 = List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala ) ) )
    val pokeList3 = List( Some( Pokemon( Simsala ) ), Some( Pokemon( Simsala ) ) )

    chars( 0 ) match
      case '1' => Some(PokeMove(pokeList1 ) )
      case '2' => Some(PokeMove( pokeList2 ))
      case '3' => Some(PokeMove( pokeList3 ))
      case _ => None


  def chooseAttack(input: String) : Option[ AttackMove ] =
    val char = input.toCharArray
    val attack = char( 0 ) match
      case '1' => AttackMove( 0 )
      case '2' => AttackMove( 1 )
      case '3' => AttackMove( 2 )
      case '4' => AttackMove( 3 )
    Some( attack )
