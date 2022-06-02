package de.htwg.se.pokelite
package model.commands

import model.states.*
import model.{ Command, GameInterface, NoInput }

import scala.util.{ Failure, Success, Try }


case class AddPlayerCommand(name:String, state:InitPlayerState) extends Command {

  override def doStep( game:GameInterface ):Try[GameInterface] = {
    if( name.isEmpty )
      Failure( NoInput )
    else {
      val newGame = game.addPlayer(name)
      if ( newGame.gamePlayer2.isEmpty)
        Success(newGame.setStateTo( InitPlayerState() ))
      else
        Success( newGame.setStateTo( InitPlayerPokemonState() ) )
    }
  }

  override def undoStep( game:GameInterface ):GameInterface =
    if game.gamePlayer2.isDefined then
      game.removePlayer()
    else game.removePlayer()
}
