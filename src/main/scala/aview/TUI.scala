package de.htwg.se.pokelite
package aview

import controller.Controller
import model.PokemonType.*
import model.*
import util.*

import java.util
import scala.io.StdIn.readLine


class TUI(controller : Controller) extends Observer :

  controller.add( this )

  override def update : Unit = println( controller.field.toString )

  def run() : Unit =
    pregame()
    inputLoop()


  def inputLoop() : Unit =
    if getName.pokemons.contents.apply(getName.currentPoke).get.isDead then
      changePokemon()
    println( getName.toString + ", choose your Attack 1, 2, 3, 4" )


    chooseAttack( readLine ) match
      case Some( move ) => controller.doAndPublish( controller.putAttack, move )
      case None =>

    inputLoop()

  def readPlayerNames() : Unit =
    print( "Enter name of Player 1: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )
    print( "Enter name of Player 2: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )

  def getName : PokePlayer = if ( controller.field.isControlledBy == 1 ) ofPlayer1 else ofPlayer2

  def ofPlayer1 : PokePlayer = controller.field.player1

  def ofPlayer2 : PokePlayer = controller.field.player2

  def choosePokemon() : Unit =

    println( getName.toString + " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )

    inputAnalysisPokemon( readLine ) match
      case None =>
      case Some( move ) => controller.doAndPublish( controller.put, move )


  def inputAnalysisPokemon(input : String) : Option[ PokeMove ] =
    val chars = input.toCharArray.toList
    val pokeList : List[ Option[ Pokemon ] ] = chars.filter( x => x.isDigit
      && x.asDigit <= PokemonType.values.length
      && x.asDigit > 0 ).map {
      case '1' => Some( Pokemon( Glurak ) )
      case '2' => Some( Pokemon( Simsala ) )
      case '3' => Some( Pokemon( Brutalanda ) )
      case '4' => Some( Pokemon( Bisaflor ) )
      case '5' => Some( Pokemon( Turtok ) )
      case _ => None
    }

    Some( PokeMove( pokeList) )

  def chooseAttack(input : String) : Option[ AttackMove ] =
    input match
      case "q" => None
      case "z" => controller.doAndPublish( controller.redo ); None
      case "y" => controller.doAndPublish( controller.undo ); None
      case "s" => println(controller.field.getCurrentPokemons); None
      case "c" =>  changePokemon(); None
      case _ =>
        val char = input.toCharArray
        val attack = char( 0 ) match
          case '1' => AttackMove( 0 )
          case '2' => AttackMove( 1 )
          case '3' => AttackMove( 2 )
          case '4' => AttackMove( 3 )
        Some( attack )


  def changePokemon():Unit=
    controller.field.getCurrentPokemons.foreach(print)
    print("Enter Number of Pokemon you want to choose: ")
    val chars = readLine.toCharArray

    if controller.field.getCurrentPokemons.indices.map(x => (x+1).toString).contains(chars(0).toString) then
      controller.doAndPublish(controller.put, ChangePokeMove(chars(0).asDigit - 1))
    else println("False Input!")



  def pregame(): Unit =
    println( controller.handle( PreEvent() ).get.toString )
    readPlayerNames()
    choosePokemon()
    choosePokemon()
    println( controller.handle( MidEvent() ).get.toString )
    println(controller.field.toString)