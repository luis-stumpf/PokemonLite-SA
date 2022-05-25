package de.htwg.se.pokelite
package model.commands

import model.*
import scala.util.Try

case class ChangeStateCommand(state : State, nextState : State) extends Command {

  override def doStep(game : Game) : Try[Game] = Try(game.setStateTo( nextState ))

  override def undoStep(game : Game) : Game = game.setStateTo( state )

}