package de.htwg.se.pokelite
package model.commands

import model.{Command, GameInterface, NoAttackSelected, PokePlayerInterface}

import de.htwg.se.pokelite.model.states.{DesicionState, FightingState, GameOverState, InitPlayerPokemonState}

import scala.util.{Failure, Success, Try}

case class AttackCommand(input:String, state:FightingState) extends Command {

  override def doStep( game:GameInterface ):Try[GameInterface] = {
    if( input.isEmpty )
      Failure( NoAttackSelected )
    else {
      val newGame = game.attackWith(input)
      if ( newGame.gameWinner.isEmpty)
        Success(newGame.setStateTo( DesicionState() ))
      else
        Success( newGame.setStateTo( GameOverState() ) )
    }
  }

  override def undoStep( game:GameInterface ):GameInterface =
    //TODO: switch to InitplayerPokmenstate when all pokemon at max hp
    game.reverseAttackWith(input)
    
  
}