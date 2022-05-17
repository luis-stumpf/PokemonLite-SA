package de.htwg.se.pokelite
package model

trait Event

case class PreEvent() extends Event

case class MidEvent() extends Event

object StateContext{
  var state = preState
  def handle(e: Event) = {
    e match {
      case pre: PreEvent => state = preState
      case mid: MidEvent => state = midState
    }
    state
  }

  def preState = println(Console.GREEN)
  def midState =
    var i = 1
    if (i == 1)
      print(Console.RED)
      i = 2
    else
      print(Console.BLUE)
      i = 1
}
