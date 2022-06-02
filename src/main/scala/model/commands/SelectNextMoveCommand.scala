package de.htwg.se.pokelite
package model.commands

import model.{Command, GameInterface, NoAttackSelected, NoDesicionMade, PokePlayerInterface, WrongInput}

import de.htwg.se.pokelite.model.states.{DesicionState, FightingState, GameOverState, InitPlayerPokemonState, SwitchPokemonState}

import scala.util.{Failure, Success, Try}

case class SelectNextMoveCommand(input:String, state:DesicionState) extends Command {

  override def doStep( game:GameInterface ):Try[GameInterface] = {
    if( input.isEmpty )
      Failure( NoDesicionMade )
    else {
      val newGame = game.setNextTurn()
      input.charAt(0).asDigit match
        case 1 => Success(newGame.setStateTo(FightingState()))
        case 2 => Success(newGame.setStateTo(SwitchPokemonState()))
        case _ => Failure( WrongInput(input) )
    }
  }

  override def undoStep( game:GameInterface ):GameInterface = game.setStateTo(state)
}