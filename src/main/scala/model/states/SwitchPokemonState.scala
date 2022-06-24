package de.htwg.se.pokelite
package model.states

import model.commands.SwitchPokemonCommand
import model.{ Command, State }

case class SwitchPokemonState( ) extends State {

  override def switchPokemonTo( input : String ) : Option[ Command ] = Some(
    SwitchPokemonCommand( input, this )
  )
}
