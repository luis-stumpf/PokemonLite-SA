package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{Attack, Brutalanda, Glurak, Move, NoPokemon, Simsala}
import util.Observer

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
    controller.doAndPublish(controller.giveControlToNextPlayer, Move())
    val input = readLine
    println(input)
    println(controller.toString)
    inputLoop()

  def getInput(): Unit =
    print("Enter name of Player 1: ")
    controller.doAndPublish(controller.setPlayerNameTo, Move(name = readLine()))
    print("Enter name of Player 2: ")
    controller.doAndPublish(controller.setPlayerNameTo, Move(name = readLine()))


  def choosePokemon(): Unit =
    if (controller.field.player1.pokemon == NoPokemon()) print(controller.field.player1.name)
    else print(controller.field.player2.name)
    println(" Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val input = readLine()
    
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.doAndPublish(controller.setPokemonTo, Move(pokemon = Glurak()))
      case '2' => controller.doAndPublish(controller.setPokemonTo, Move(pokemon = Simsala()))
      case '3' => controller.doAndPublish(controller.setPokemonTo, Move(pokemon = Brutalanda()))
      case _ => controller.doAndPublish(controller.setPokemonTo, Move())



