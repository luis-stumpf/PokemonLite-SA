package controller.commands

import model.*
import model.State.*
import util.Command

import scala.util.{ Failure, Success, Try }

case class AddPokemonCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    game.interpretPokemonSelectionFrom( input )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.removePokemonFromPlayer() )
}
