package de.htwg.se.pokelite
package model.states

import model.commands.*
import model.{Command, GameInterface, State}

case class InitPlayerPokemonState( ) extends State {

  override def addPokemons( name : String ) : Option[ Command ] = Some(
    AddPokemonCommand( name, this )
  )


  override def restartTheGame(game: GameInterface): Option[Command] =
    Some(GameOverCommand(game, this))

}
