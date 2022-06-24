package de.htwg.se.pokelite
package model.states

import model.{ Command, State }
import model.commands.SwitchPokemonCommand

case class SwitchPokemonState( ) extends State {

  override def switchPokemonTo( input : String ) : Option[ Command ] = Some(
    SwitchPokemonCommand( input, this )
  )
}
