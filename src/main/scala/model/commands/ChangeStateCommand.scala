package de.htwg.se.pokelite
package model.commands

import model.*

import scala.util.Try

case class ChangeStateCommand( state : State, nextState : State ) extends Command {

  override def doStep( game : GameInterface ) : Try[ GameInterface ] = Try( game.setStateTo( nextState ) )

  override def undoStep( game : GameInterface ) : GameInterface = game.setStateTo( state )

}