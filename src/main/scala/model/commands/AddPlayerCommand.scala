package de.htwg.se.pokelite
package model.commands

import model.State.*
import model.Game
import model.Error

import scala.util.{ Failure, Success, Try }


case class AddPlayerCommand(name:String, state:InitPlayerState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( name.isEmpty )
      Failure( NoPlayerName )
    else {
      val newGame = game.addPlayer(name)
      if ( newGame.player2.isEmpty)
        Success(newGame.setStateTo( InitPlayerState() ))
      else
        Success( newGame.setStateTo( InitPlayerPokemon() ) )
    }
  }

  override def undoStep( game:Game ):Game = game.copy(
    state = state, if game.player2.isDefined then player2 = None else player1 = None
  )
}
