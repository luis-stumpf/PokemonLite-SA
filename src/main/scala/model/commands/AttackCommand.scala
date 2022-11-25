package de.htwg.se.pokelite
package model.commands

import model.*
import model.states.{ DesicionState, FightingState, GameOverState, InitPlayerPokemonState }

import scala.util.{ Failure, Success, Try }

case class AttackCommand( input : String, state : FightingState ) extends Command {

  override def doStep( game : GameInterface ) : Try[ GameInterface ] = {
    game.interpretAttackSelectionFrom( input ) match
      case Failure( x ) => Failure( x )
      case Success( updatedGame ) =>
        if updatedGame.hasWinner then Success( updatedGame.setStateTo( GameOverState() ) )
        else Success( updatedGame.setStateTo( DesicionState() ) )

  }

  override def undoStep( game : GameInterface ) : GameInterface =
    game.reverseAttackWith( input )
}