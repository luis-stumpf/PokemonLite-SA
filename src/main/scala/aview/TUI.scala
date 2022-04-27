package de.htwg.se.pokelite
package aview

import controller.Controller
import util.Observer

import de.htwg.se.pokelite.model.{Attack, Pokemon}

import scala.io.StdIn.readLine


class TUI(controller: Controller) extends Observer:
  controller.add(this)

  override def update = println(controller.field.toString)

  def run =
    println(controller.field.toString)
    getInput()
    chosePokemonP1()
    chosePokemonP2()



  def getInput(): Unit =
    println("Enter name of Player 1: ")
    controller.setNameP1(readLine())
    println("Enter name of Player 2: ")
    controller.setNameP2(readLine())


  def chosePokemonP1(): Unit =
    println(controller.field.namePlayer1 + " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val attackList = List(Attack("Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite",15), Attack("Tackle", 10))

    val input = readLine
    val glurak = Pokemon("Glurak", 150, attackList)
    val simsala = Pokemon("Simsala", 130, attackList)
    val brutalanda = Pokemon("Brutalanda", 180, attackList)
    val noPokemon = Pokemon("No Pokemon", -1, attackList)
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.setPokemonP1(glurak)
      case '2' => controller.setPokemonP1(simsala)
      case '3' => controller.setPokemonP1(brutalanda)
      case _ => controller.setPokemonP1(noPokemon)

  def chosePokemonP2(): Unit =
    println(controller.field.namePlayer2 + " Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val attackList = List(Attack("Flammewurf", 30))
    val input = readLine
    val glurak = Pokemon("Glurak", 150, attackList)
    val simsala = Pokemon("Simsala", 130, attackList)
    val brutalanda = Pokemon("Brutalanda", 180, attackList)
    val noPokemon = Pokemon("No Pokemon", -1, attackList)
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.setPokemonP2(glurak)
      case '2' => controller.setPokemonP2(simsala)
      case '3' => controller.setPokemonP2(brutalanda)
      case _ => controller.setPokemonP2(noPokemon)


