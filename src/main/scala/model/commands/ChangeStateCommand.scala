package de.htwg.se.pokelite
package model.commands

import model.*


case class ChangeStateCommand(state : State, nextState : State) extends Command {

  override def doStep(game : Game) : Game = game.setState( nextState )

  override def undoStep(game : Game) : Game = game.setState( state )

}