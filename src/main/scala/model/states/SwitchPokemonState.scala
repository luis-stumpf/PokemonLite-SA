package de.htwg.se.pokelite
package model.states

import model.commands.{GameOverCommand, SwitchPokemonCommand}
import model.{Command, GameInterface, State}

case class SwitchPokemonState( ) extends State {

  override def switchPokemonTo( input : String ) : Option[ Command ] = Some(
    SwitchPokemonCommand( input, this )
  )

  override def restartTheGame(game: GameInterface): Option[Command] =
    Some(GameOverCommand(game, this))
}
