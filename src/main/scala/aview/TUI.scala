package de.htwg.se.pokelite
package aview

import controller.Controller
import util.Observer

import de.htwg.se.pokelite.model.Pokemon

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
    val inputP1 = readLine
    controller.setNameP1(inputP1)
    val inputP2 = readLine()
    controller.setNameP2(inputP2)



  def chosePokemonP1(): Unit =
    println("Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val input = readLine
    val glurak = Pokemon("Glurak", 150)
    val simsala = Pokemon("Simsala", 130)
    val brutalanda = Pokemon("Brutalanda", 180)
    val noPokemon = Pokemon("No Pokemon", -1)
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.setPokemonP1(glurak)
      case '2' => controller.setPokemonP1(simsala)
      case '3' => controller.setPokemonP1(brutalanda)
      case _ => controller.setPokemonP1(noPokemon)

  def chosePokemonP2(): Unit =
    println("Choose your Pokemon: \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n")

    val input = readLine
    val glurak = Pokemon("Glurak", 150)
    val simsala = Pokemon("Simsala", 130)
    val brutalanda = Pokemon("Brutalanda", 180)
    val noPokemon = Pokemon("No Pokemon", -1)
    val chars = input.toCharArray
    chars(0) match
      case '1' => controller.setPokemonP2(glurak)
      case '2' => controller.setPokemonP2(simsala)
      case '3' => controller.setPokemonP2(brutalanda)
      case _ => controller.setPokemonP2(noPokemon)


