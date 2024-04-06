package de.htwg.se.pokelite
package model.commands

import model.*

import scala.util.{ Try, Success }

case class ChangeStateCommand( state: State, nextState: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] = Try(
    game.setStateTo( nextState )
  )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.setStateTo( state ) )

}
