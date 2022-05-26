package de.htwg.se.pokelite
package model.states

import model.{ Command, State }
import model.commands.*

case class FightingState() extends State {

  override def attackWith(input : String) : Option[ Command ] = Some(
    AttackCommand( input, this )
  )

}