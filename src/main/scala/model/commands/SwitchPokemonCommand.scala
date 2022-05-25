package de.htwg.se.pokelite
package model.commands

import model.{ Command, Error, Game, NoInput }

import de.htwg.se.pokelite.model.states.FightingState

import scala.util.{ Failure, Success, Try }

case class SwitchPokemonCommand(input:String, state:FightingState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( input.isEmpty )
      Failure( NoInput )
    else {
      Success(game.selectPokemon(input))
    }
  }

  override def undoStep( game:Game ): Game = game.selectPokemon(input)

}