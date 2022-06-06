package de.htwg.se.pokelite
package model.states

import model.{ Command, State }

import de.htwg.se.pokelite.model.commands.ChangeStateCommand

case class GameOverState() extends State {
  
  override def restartTheGame():Option[Command] = Some(
    ChangeStateCommand(this, InitState()))
}
