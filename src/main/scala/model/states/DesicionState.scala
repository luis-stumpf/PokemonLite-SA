package de.htwg.se.pokelite
package model.states

import model.{ Command, State }
import model.commands.*

case class DesicionState() extends State {

  override def nextMove(input : String) : Option[ Command ] = Some(
    SelectNextMoveCommand(input, this)
  )

}