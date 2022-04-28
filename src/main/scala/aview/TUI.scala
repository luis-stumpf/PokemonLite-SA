package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{Attack, NoPokemon, Glurak, Simsala, Brutalanda}
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
    controller.giveControlToNextPlayer()
    val input = readLine
    println(input)
    println(controller.toString)
    inputLoop()

  def getInput(): Unit =
    print("Enter name of Player 1: ")
    controller.setPlayerNameTo(readLine())
    print("Enter name of Player 2: ")
    controller.setPlayerNameTo(readLine())


  def choosePokemon(): Unit =
    if (controller.field.player1.pokemon == NoPokemon()) print(controller.field.player1.name)
    else print(controller.field.player2.name)
    println(" Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val input = readLine()
    
    // TODO: ich glaube das zeug muss der Controller setzen die TUI sollte nicht direkt auf ein Model zugreifen

    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.setPokemonTo(Glurak())
      case '2' => controller.setPokemonTo(Simsala())
      case '3' => controller.setPokemonTo(Brutalanda())
      case _ => controller.setPokemonTo(NoPokemon())



