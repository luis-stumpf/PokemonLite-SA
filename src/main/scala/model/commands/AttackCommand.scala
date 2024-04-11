package de.htwg.se.pokelite
package model.commands

import model.*
import model.State.*

import scala.util.{ Failure, Success, Try }

case class AttackCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] = {
    game.interpretAttackSelectionFrom( input ) match
      case Failure( x ) => Failure( x )
      case Success( updatedGame ) =>
        if updatedGame.hasWinner then
          Success( updatedGame.setStateTo( GameOverState ) )
        else Success( updatedGame.setStateTo( DesicionState ) )

  }

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.reverseAttackWith( input ) )
}
