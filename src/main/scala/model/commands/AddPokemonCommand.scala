package de.htwg.se.pokelite
package model.commands

import model.Game

import de.htwg.se.pokelite.model.state.InitPlayerPokemon

import scala.util.{ Failure, Success, Try }

case class AddPokemonCommand(list:String, state:InitPlayerPokemon) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( list.isEmpty )
      Failure( NoPokemonSelected )
    else {
      val newGame = game.addPokemonToPlayer(list)
      if ( newGame.player2.get.pokemons.contents.isEmpty)
        Success(newGame.setStateTo( InitPlayerPokemon() ))
      else
        Success( newGame.setStateTo( FightingState() ) )
    }
  }

  override def undoStep( game:Game ):Game = game.copy(
    state = state, if game.player2.isDefined then player2 = None else player1 = None
  )
}
