package de.htwg.se.pokelite
package model.commands

import model.{ Command, GameInterface, NoPokemonSelected, PokePlayerInterface, PokePack }

import de.htwg.se.pokelite.model.states.*

import scala.util.{ Failure, Success, Try }

case class AddPokemonCommand(list:String, state:InitPlayerPokemonState) extends Command {

  override def doStep( game:GameInterface ):Try[GameInterface] = {
    if( list.isEmpty )
      Failure( NoPokemonSelected )
    else {
      val newGame = game.addPokemonToPlayer(list)
      if ( newGame.gamePlayer2.get.getPokemons == PokePack(List(None)))
        Success(newGame.setStateTo( InitPlayerPokemonState() ))
      else
        Success( newGame.setStateTo( DesicionState() ) )
    }
  }

  override def undoStep( game:GameInterface ):GameInterface =
    if game.gamePlayer1.get.getPokemons.contents.isEmpty then
      game.setStateTo(InitPlayerState())
    else game.removePokemonFromPlayer()
}
