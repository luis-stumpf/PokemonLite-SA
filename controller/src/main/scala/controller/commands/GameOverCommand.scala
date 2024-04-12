package controller.commands

import model.impl.game.Game
import model.{ GameInterface, State }
import util.Command

import scala.util.{ Try, Success }

case class GameOverCommand( oldGame: GameInterface, state: State )
    extends Command[GameInterface] {

  def doStep( game: GameInterface ): Try[GameInterface] = Try( Game() )

  def undoStep( game: GameInterface ): Try[GameInterface] = Success( oldGame )

}
