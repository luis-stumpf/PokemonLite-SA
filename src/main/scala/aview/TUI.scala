package de.htwg.se.pokelite
package aview

import controller.Controller
import util.Observer
import scala.io.StdIn.readLine


class TUI(controller: Controller) extends Observer:
  controller.add(this)

  override def update = println(controller.field.toString)

  def run =
    println(controller.field.toString)
    getInput();



  def getInput(): Unit =
    val inputP1 = readLine
    controller.setNameP1(inputP1)
    val inputP2 = readLine()
    controller.setNameP2(inputP2)


