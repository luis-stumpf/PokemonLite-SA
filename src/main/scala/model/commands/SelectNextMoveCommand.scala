package de.htwg.se.pokelite
package model.commands

import model.*
import model.states.*

import scala.util.{ Failure, Success, Try }

case class SelectNextMoveCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] = {
    if input.isEmpty then Failure( NoInput )
    else
      input.charAt( 0 ).asDigit match
        case 1 => Success( game.setStateTo( FightingState() ) )
        case 2 => Success( game.setStateTo( SwitchPokemonState() ) )
        case _ => Failure( WrongInput( input ) )

  }

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Success( game.setStateTo( state ) )
}
