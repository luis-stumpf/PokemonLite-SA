package de.htwg.se.pokelite
package model.commands

import model.{ Command, Game, NoPokemonSelected, PokePlayer, PokePack }

import de.htwg.se.pokelite.model.states.*

import scala.util.{ Failure, Success, Try }

case class AddPokemonCommand(list:String, state:InitPlayerPokemonState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( list.isEmpty )
      Failure( NoPokemonSelected )
    else {
      val newGame = game.addPokemonToPlayer(list)
      if ( newGame.player2.get.pokemons == PokePack(List(None)))
        Success(newGame.setStateTo( InitPlayerPokemonState() ))
      else
        Success( newGame.setStateTo( DesicionState() ) )
    }
  }

  override def undoStep( game:Game ):Game =
    if game.player1.get.pokemons.contents.isEmpty then
      game.copy(state = InitPlayerState())
    else if game.player2.get.pokemons.contents.nonEmpty then
      game.copy(state = state, player2 = Some(PokePlayer(game.player2.get.name, PokePack(List(None)))))
    else
      game.copy(state = state, player1 = Some(PokePlayer(game.player1.get.name, PokePack(List(None)))))
}
