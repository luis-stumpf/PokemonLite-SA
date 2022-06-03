package de.htwg.se.pokelite
package model.states

import model.Command
import model.State

import de.htwg.se.pokelite.model.commands.SwitchPokemonCommand

case class SwitchPokemonState() extends State {

  override def switchPokemonTo(input:String): Option[Command] = Some(
    SwitchPokemonCommand(input, this)
  )
}
