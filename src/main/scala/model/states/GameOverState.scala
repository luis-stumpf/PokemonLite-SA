package de.htwg.se.pokelite
package model.states

import model.{ Command, GameInterface, State }

import de.htwg.se.pokelite.model.commands.{ ChangeStateCommand, GameOverCommand }

case class GameOverState() extends State {
  
  override def restartTheGame(game:GameInterface):Option[Command] =
    Some(GameOverCommand(game, this))
}
