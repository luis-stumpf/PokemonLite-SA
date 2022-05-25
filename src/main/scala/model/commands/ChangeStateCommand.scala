package de.htwg.se.pokelite
package model.commands

import model._

import scala.util.{ Success, Try }


case class ChangeStateCommand( state:State, nextState:State, info:Option[Info] = None ) extends Command {

  override def doStep( game:Game ):Try[(Game, Option[Info])] = Success( game.setState( nextState ), info )

  override def undoStep( game:Game ):Game = game.setState( state )
  
}