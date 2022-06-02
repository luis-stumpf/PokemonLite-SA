package de.htwg.se.pokelite
package model.commands

import model.{Command, Error, GameInterface, NoInput}

import de.htwg.se.pokelite.model.states.{DesicionState, FightingState, SwitchPokemonState}

import scala.util.{Failure, Success, Try}

case class SwitchPokemonCommand(input:String, state:SwitchPokemonState) extends Command {

  override def doStep( game:GameInterface ):Try[GameInterface] = {
    if( input.isEmpty )
      Failure( NoInput )
      //TODO: Richtige if abrage
    else {
      val newGame = game.selectPokemon((input.charAt(0).asDigit))
      Success(newGame.setStateTo(DesicionState()))
    }
  }

  override def undoStep( game:GameInterface ): GameInterface =
    val newGame = game.selectPokemon(input.charAt(0).asDigit)
    newGame.setStateTo(state)

}