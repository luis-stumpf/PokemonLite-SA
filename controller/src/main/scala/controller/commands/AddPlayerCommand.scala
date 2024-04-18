package controller.commands

import model.*
import model.State.*
import util.Command

import scala.util.{ Failure, Success, Try }
import util.WrongState

case class AddPlayerCommand( name: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    if game.state != InitPlayerState then Failure( WrongState )
    else game.addPlayerWith( name )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.removePlayer() )

}
