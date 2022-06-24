package de.htwg.se.pokelite
package model.states

import model.commands.SelectNextMoveCommand
import model.{ Command, State }

case class DesicionState( ) extends State {

  override def nextMove( input : String ) : Option[ Command ] = Some(
    SelectNextMoveCommand( input, this )
  )

}