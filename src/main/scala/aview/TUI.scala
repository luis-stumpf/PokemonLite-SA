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
    getInput();
    chosePokemons()



  def getInput(): Unit =
    val inputP1 = readLine
    controller.setNameP1(inputP1)
    val inputP2 = readLine()
    controller.setNameP2(inputP2)

  def chosePokemons(): Unit =
    val P1 =  Pokemon("Glurak", 150)
    val P2 = Pokemon("Simsala", 130)
    controller.setPokemonP1(P1)
    controller.setPokemonP2(P2)


