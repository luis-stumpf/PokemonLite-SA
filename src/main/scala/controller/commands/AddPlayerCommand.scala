package controller.commands

import model.*
import model.impl.game.Game
import model.State.*
import util.Command

import scala.util.{ Failure, Success, Try }

case class AddPlayerCommand( name: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    game.addPlayerWith( name )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.removePlayer() )

}
