package de.htwg.se.pokelite
package model.commands

import model.states.*
import model.{ Command, Game, NoInput }

import scala.util.{ Failure, Success, Try }


case class AddPlayerCommand(name:String, state:InitPlayerState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( name.isEmpty )
      Failure( NoInput )
    else {
      val newGame = game.addPlayer(name)
      if ( newGame.player2.isEmpty)
        Success(newGame.setStateTo( InitPlayerState() ))
      else
        Success( newGame.setStateTo( InitPlayerPokemonState() ) )
    }
  }

  override def undoStep( game:Game ):Game =
    if game.player2.isDefined then
      game.copy(state = state, player2 = None)
    else game.copy(state = state, player1 = None)
}
