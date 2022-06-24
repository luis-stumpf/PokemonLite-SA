package de.htwg.se.pokelite
package model.states

import model.commands.*
import model.{Command, State}

case class InitPlayerPokemonState() extends State {

  override def addPokemons(name: String): Option[Command] = Some(
    AddPokemonCommand(name, this)
  )

}
