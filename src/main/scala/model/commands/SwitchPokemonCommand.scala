package de.htwg.se.pokelite
package model.commands

import model.states.{ DesicionState, FightingState, SwitchPokemonState }
import model.{ Command, Error, GameInterface, NoInput }

import scala.util.{ Failure, Success, Try }
import de.htwg.se.pokelite.model.State

case class SwitchPokemonCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    game.selectPokemonFrom( input )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    val newGame = game.selectPokemonFrom( input )
    Success( newGame.get.setStateTo( state ) )

}
