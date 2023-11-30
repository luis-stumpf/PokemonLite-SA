package de.htwg.se.pokelite
package model.states

import model.commands.*
import model.{Command, GameInterface, State}

case class InitPlayerState( ) extends State {

  override def addPlayer( name : String ) : Option[ Command ] = Some(
    AddPlayerCommand( name, this )
  )

  override def restartTheGame(game: GameInterface): Option[Command] =
    Some(GameOverCommand(game, this))
}
