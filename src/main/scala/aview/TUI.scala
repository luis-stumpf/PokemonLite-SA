package de.htwg.se.pokelite
package aview

import controller.Controller
import model.{Attack, NoPokemon, Pokemon}
import util.Observer

import scala.io.StdIn.readLine


class TUI(controller: Controller) extends Observer:
  controller.add(this)

  override def update = println(controller.field.toString)

  def run =
    println(controller.field.toString)
    getInput()
    choosePokemon()
    choosePokemon()
    inputLoop()


  def inputLoop(): Unit =
    controller.giveControlToNextPlayer()
    val input = readLine
    /*
    input match
      case "q" =>
      case _ => {
        val chars = input.toCharArray
        val stone = chars(0) match
          case 'X' => Stone.X
          case 'x' => Stone.X
          case 'O' => Stone.O
          case 'o' => Stone.O
          case _   => Stone.Empty
        val x = chars(1).toString.toInt
        val y = chars(2).toString.toInt
        controller.put(stone, x, y)
    */
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

    val attackList = List(Attack("Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite",15), Attack("Tackle", 10))
    val attackList2 = List(Attack("Kackawurf", 30), Attack("Blitzdonner", 20), Attack("Slap",15), Attack("Nothing", 10))
    
    val input = readLine()
    
    // TODO: ich glaube das zeug muss der Controller setzen die TUI sollte nicht direkt auf ein Model zugreifen
    val glurak = Pokemon("Glurak", 150, attackList)
    val simsala = Pokemon("Simsala", 130, attackList2)
    val brutalanda = Pokemon("Brutalanda", 180, attackList2)
    val noPokemon = Pokemon("No Pokemon", -1, attackList)
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.setPokemonTo(glurak)
      case '2' => controller.setPokemonTo(simsala)
      case '3' => controller.setPokemonTo(brutalanda)
      case _ => controller.setPokemonTo(noPokemon)



