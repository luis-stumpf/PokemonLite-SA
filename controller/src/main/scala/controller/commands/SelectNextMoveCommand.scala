package controller.commands

import model.*
import model.State.*
import util.Command
import util.NoInput
import util.WrongInput

import scala.util.{ Failure, Success, Try }
import util.WrongState

case class SelectNextMoveCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] = {
    if game.state != DesicionState then return Failure( WrongState )
    if input.isEmpty then Failure( NoInput )
    else
      input.charAt( 0 ).asDigit match
        case 1 => Success( game.setStateTo( FightingState ) )
        case 2 => Success( game.setStateTo( SwitchPokemonState ) )
        case _ => Failure( WrongInput( input ) )

  }

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.setStateTo( state ) )
}
