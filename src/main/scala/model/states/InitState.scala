package de.htwg.se.pokelite
package model.states

import model.*
import model.commands.*
import util.*

case class InitState( ) extends State {

  override def initPlayers( ) : Option[ Command ] = Some(
    ChangeStateCommand( this, InitPlayerState() )
  )


  override def restartTheGame(game: GameInterface): Option[Command] =
    Some(GameOverCommand(game, this))

}
