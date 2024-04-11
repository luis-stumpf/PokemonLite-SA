package de.htwg.se.pokelite
package controller.commands

import model.{ Error, GameInterface, NoInput }
import model.State.*

import scala.util.{ Failure, Success, Try }
import de.htwg.se.pokelite.model.State
import de.htwg.se.pokelite.util.Command

case class SwitchPokemonCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    game.selectPokemonFrom( input )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    val newGame = game.selectPokemonFrom( input )
    Success( newGame.get.setStateTo( state ) )

}
