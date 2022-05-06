package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{Attack, Move, PlayerMove, PokeMove, Pokemon, PokemonType, ControlMove}
import util.Observer
import de.htwg.se.pokelite.model.PokemonType.{Glurak, Simsala, Brutalanda}

import scala.io.StdIn.readLine


class TUI(controller: Controller) extends Observer:
  controller.add(this)

  override def update: Unit = println(controller.field.toString)

  def run(): Unit =
    println(controller.field.toString)
    getInput()
    choosePokemon()
    choosePokemon()
    inputLoop()


  def inputLoop(): Unit =
    controller.doAndPublish(controller.put, ControlMove())
    val input = readLine
    println(input)
    println(controller.toString)
    inputLoop()

  def getInput(): Unit =
    print("Enter name of Player 1: ")
    controller.doAndPublish(controller.put, PlayerMove(readLine()))
    print("Enter name of Player 2: ")
    controller.doAndPublish(controller.put, PlayerMove(readLine()))


  def choosePokemon(): Unit =
    if (controller.field.player1.pokemon.isEmpty ) print(controller.field.player1.name)
    else print(controller.field.player2.name)
    println(" Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val input = readLine()
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.doAndPublish(controller.put, PokeMove(Pokemon( Glurak )))
      case '2' => controller.doAndPublish(controller.put, PokeMove(Pokemon( Simsala )))
      case '3' => controller.doAndPublish(controller.put, PokeMove(Pokemon( Brutalanda )))
      case _ => controller.doAndPublish(controller.put, PokeMove(Pokemon(Glurak)))



