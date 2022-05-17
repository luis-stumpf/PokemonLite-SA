package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{Attack, AttackMove, Move, PlayerMove, PokeMove, Pokemon, PokemonType}
import util.Observer

import de.htwg.se.pokelite.model.PokemonType.{Brutalanda, Glurak, Simsala}

import java.util
import scala.io.StdIn.readLine


class TUI(controller : Controller) extends Observer :
  val ANZAHL_POKEMON = 3
  controller.add( this )

  override def update : Unit = println( controller.field.toString )

  def run() : Unit =
    println( controller.field.toString )
    getPlayerNames()
    choosePokemon()
    choosePokemon()
    inputLoop()


  def inputLoop() : Unit =

    if aPlayerHasWon then
      println(getName + " has won the game!")
      return null

    println( getName + ", choose your Attack 1, 2, 3, 4" )

    chooseAttack(readLine) match
      case Some(move) => controller.doAndPublish(controller.put, move)
      case None =>
    inputLoop()

  def getPlayerNames() : Unit =
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

  def aPlayerHasWon : Boolean =
    if (controller.field.player1.pokemons(0).get.hp <= 0
      || controller.field.player2.pokemons(0).get.hp <= 0) return true
    false

  def inputAnalysisPokemon(input: String): Option[PokeMove] =
    // Warning: Max allowed number of available Pokemon is 9. Because of toChar: '1','9'
    val chars = input.toCharArray.toList
    val pokeList  : List[Option[Pokemon]] = chars.filter(x => x.isDigit
        && x.asDigit <= ANZAHL_POKEMON
        && x.asDigit > 0).map {
      case '1' => Some(Pokemon(Glurak))
      case '2' => Some(Pokemon(Simsala))
      case '3' => Some(Pokemon(Brutalanda))
      case _ => None
    }

    pokeList.foreach(println)

    Some(PokeMove(pokeList.take(ANZAHL_POKEMON)))




  def chooseAttack(input: String) : Option[ AttackMove ] =
    val char = input.toCharArray
    val attack = char( 0 ) match
      case '1' => AttackMove( 0 )
      case '2' => AttackMove( 1 )
      case '3' => AttackMove( 2 )
      case '4' => AttackMove( 3 )
    Some( attack )
