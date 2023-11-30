package de.htwg.se.pokelite
package model.states

import model.commands.{GameOverCommand, SelectNextMoveCommand}
import model.{Command, GameInterface, State}

case class DesicionState( ) extends State {

  override def nextMove( input : String ) : Option[ Command ] = Some(
    SelectNextMoveCommand( input, this )
  )

  override def restartTheGame(game: GameInterface): Option[Command] =
    Some(GameOverCommand(game, this))

}