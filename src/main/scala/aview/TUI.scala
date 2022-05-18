package de.htwg.se.pokelite
package aview

import controller.Controller
import model.PokemonType.*
import model.*
import util.*

import java.util
import scala.io.StdIn.readLine


class TUI(controller : Controller) extends Observer :
  val ANZAHL_POKEMON = 3 // TODO: Move Game-Logic Constants to a config file or seperate class
  controller.add( this )

  override def update : Unit = println( controller.field.toString )

  def run() : Unit =
    println( controller.field.toString )
    println( controller.handle( PreEvent() ).get.toString )
    readPlayerNames()
    choosePokemon()
    choosePokemon()
    println( controller.handle( MidEvent() ).get.toString )
    inputLoop()


  def inputLoop() : Unit =
    println( getName + ", choose your Attack 1, 2, 3, 4" )

    chooseAttack( readLine ) match
      case Some( move ) => controller.doAndPublish( controller.putAttack, move )
      case None =>

    if aPlayerHasWon then
      println( controller.handle( EndEvent() ).get.toString )
      println( getName + " has won the game!" )
      return null

    inputLoop()

  def readPlayerNames() : Unit =
    print( "Enter name of Player 1: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )
    print( "Enter name of Player 2: " )
    controller.doAndPublish( controller.put, PlayerMove( readLine() ) )

  def getName : String = if ( controller.field.isControlledBy == 1 ) ofPlayer1 else ofPlayer2

  def ofPlayer1 : String = controller.field.player1.name

  def ofPlayer2 : String = controller.field.player2.name

  def choosePokemon() : Unit =

    println( getName + " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n" )

    inputAnalysisPokemon( readLine ) match
      case None =>
      case Some( move ) => controller.doAndPublish( controller.put, move )

  def aPlayerHasWon : Boolean =
    if ( controller.field.player1.pokemons.contents( 0 ).get.hp <= 0
      || controller.field.player2.pokemons.contents( 0 ).get.hp <= 0 ) return true
    false

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

    Some( PokeMove( PokePack(pokeList.take( ANZAHL_POKEMON )) ) )


  def chooseAttack(input : String) : Option[ AttackMove ] =
    input match
      case "q" => None
      case "z" => controller.doAndPublish( controller.redo ); None
      case "y" => controller.doAndPublish( controller.undo ); None
      case _ =>
        val char = input.toCharArray
        val attack = char( 0 ) match
          case '1' => AttackMove( 0 )
          case '2' => AttackMove( 1 )
          case '3' => AttackMove( 2 )
          case '4' => AttackMove( 3 )
        Some( attack )
