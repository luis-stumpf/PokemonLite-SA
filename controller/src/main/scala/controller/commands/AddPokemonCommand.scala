package controller.commands

import model.*
import model.State.*
import util.Command

import scala.util.{ Failure, Success, Try }
import util.WrongState

case class AddPokemonCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    if game.state != InitPlayerPokemonState then Failure( WrongState )
    else game.interpretPokemonSelectionFrom( input )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.removePokemonFromPlayer() )
}
