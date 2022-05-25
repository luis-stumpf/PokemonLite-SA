package de.htwg.se.pokelite
package model.states

import model.{ Command, State }

case class FightingState() extends State {

  override def attackWith(input : String) : Option[ Command ] = Some(
    AttackCommand( input, this )
  )

  override def switchPokemon(input: String): Option[Command] = Some(
    SwitchPokemonCommand(input, this)
  )

}