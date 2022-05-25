package de.htwg.se.pokelite
package model.commands

import model.{ Game, PokePlayer }

import de.htwg.se.pokelite.model.states.{ FightingState, InitPlayerPokemonState }

import scala.util.{ Failure, Success, Try }

case class AttackCommand(input:String, state:FightingState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( input.isEmpty )
      Failure( NoAttackSelected )
    else {
      val newGame = game.attackWith(input)
      if ( newGame.winner.isEmpty)
        Success(newGame.setStateTo( FightingState() ))
      else
        Success( newGame.setStateTo( GameOverState() ) )
    }
  }

  override def undoStep( game:Game ):Game =
    //TODO: switch to InitplayerPokmenstate when all pokemon at max hp
    game.reverseAttackWith(input)
    
  
}