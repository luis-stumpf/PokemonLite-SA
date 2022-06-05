package de.htwg.se.pokelite
package model.commands

import model.{ Command, GameInterface, NoValidAttackSelected, NoInput, PokePlayerInterface }

import de.htwg.se.pokelite.model.states.{ DesicionState, FightingState, GameOverState, InitPlayerPokemonState }

import scala.util.{ Failure, Success, Try }

case class AttackCommand(input:String, state:FightingState) extends Command {

  override def doStep(game : GameInterface) : Try[ GameInterface ] = {
    val newGame = game.interpretAttackSelectionFrom( input )
    if ( newGame.get.winner.isEmpty )
      Success( newGame.get.setStateTo( DesicionState() ) )
    else
      Success( newGame.get.setStateTo( GameOverState() ) )
  }

  override def undoStep(game : GameInterface) : GameInterface =
  //TODO: switch to InitplayerPokmenstate when all pokemon at max hp
    game.reverseAttackWith(input)

}