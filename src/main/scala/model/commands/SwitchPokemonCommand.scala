package de.htwg.se.pokelite
package model.commands

import model.{Command, Error, Game, NoInput}

import de.htwg.se.pokelite.model.states.{FightingState, SwitchPokemonState}

import scala.util.{Failure, Success, Try}

case class SwitchPokemonCommand(input:String, state:SwitchPokemonState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( input.isEmpty )
      Failure( NoInput )
      //TODO: Richtige if abrage
    else {
      Success(game.selectPokemon(input))
    }
  }

  override def undoStep( game:Game ): Game = game.selectPokemon(input)

}