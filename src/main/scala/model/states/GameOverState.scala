package de.htwg.se.pokelite
package model.states

import model.commands.{ ChangeStateCommand, GameOverCommand }
import model.{ Command, GameInterface, State }

case class GameOverState( ) extends State {

  override def restartTheGame( game : GameInterface ) : Option[ Command ] =
    Some( GameOverCommand( game, this ) )
}
