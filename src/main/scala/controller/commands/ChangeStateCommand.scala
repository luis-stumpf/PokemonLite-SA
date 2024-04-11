package de.htwg.se.pokelite
package controller.commands

import model.*
import de.htwg.se.pokelite.util.Command

import scala.util.{ Try, Success }

case class ChangeStateCommand( state: State, nextState: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] = Try(
    game.setStateTo( nextState )
  )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.setStateTo( state ) )

}
