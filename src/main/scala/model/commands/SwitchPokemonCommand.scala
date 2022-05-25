package de.htwg.se.pokelite
package model.commands

import model.Game

import de.htwg.se.pokelite.model.states.FightingState

import scala.util.{ Failure, Success, Try }

case class SwitchPokemonCommand(input:String, state:FightingState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( input.isEmpty )
      Failure( NoInput )
    else {
      val newGame = game.selectPokemon(input)
    }
  }

  override def undoStep( game:Game ):Game = game.copy(
    state = state, if game.player2.isDefined then player2 = None else player1 = None
  )
}