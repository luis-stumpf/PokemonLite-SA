package de.htwg.se.pokelite
package model.states

import model.commands.*
import model.{Command, GameInterface, State}

case class FightingState( ) extends State {

  override def attackWith( input : String ) : Option[ Command ] = Some(
    AttackCommand( input, this )
  )


  override def restartTheGame(game: GameInterface): Option[Command] =
    Some(GameOverCommand(game, this))

}