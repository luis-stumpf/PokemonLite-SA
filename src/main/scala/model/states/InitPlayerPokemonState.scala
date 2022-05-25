package de.htwg.se.pokelite
package model.states

import model.{ Command, State }
import model.commands.*

case class InitPlayerPokemonState() extends State {

  override def addPokemons(name : String) : Option[ Command ] = Some(
    AddPokemonCommand( name, this )
  )
  
}
