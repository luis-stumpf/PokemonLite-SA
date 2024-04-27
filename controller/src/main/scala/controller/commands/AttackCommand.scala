package controller.commands

import model.*
import model.State.*
import util.Command

import scala.util.{ Failure, Success, Try }
import util.WrongState

case class AttackCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] = {
    if game.state != FightingState then Failure( WrongState )
    else
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
