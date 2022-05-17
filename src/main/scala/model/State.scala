package de.htwg.se.pokelite
package model

trait Event

case class PreEvent() extends Event

case class P1Event() extends Event

case class P2Event() extends Event

object StateContext{
  var state = p1State
  def handle(e: Event) = {
    e match {
      case preState: PreEvent => state = preState
      case p1: P1Event => state = p1
      case p2: P2Event => state = p2
    }
    state
  }
  def preState = print(Console.GREEN)
  def p1State = println(Console.RED)
  def p2State = println(Console.BLUE)
}
