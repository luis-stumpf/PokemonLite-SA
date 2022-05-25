package de.htwg.se.pokelite
package model.state

import model.{ Command, State }

case class FightingState() extends State {

  override def attack(input : String) : Option[ Command ] = Some(
    AttackCommand( input, this )
  )

}