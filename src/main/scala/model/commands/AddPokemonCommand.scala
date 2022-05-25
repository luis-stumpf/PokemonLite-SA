package de.htwg.se.pokelite
package model.commands

import model.{ Game, PokePlayer }

import de.htwg.se.pokelite.model.states.InitPlayerPokemonState

import scala.util.{ Failure, Success, Try }

case class AddPokemonCommand(list:String, state:InitPlayerPokemonState) extends Command {

  override def doStep( game:Game ):Try[Game] = {
    if( list.isEmpty )
      Failure( NoPokemonSelected )
    else {
      val newGame = game.addPokemonToPlayer(list)
      if ( newGame.player2.get.pokemons.contents.isEmpty)
        Success(newGame.setStateTo( InitPlayerPokemonState() ))
      else
        Success( newGame.setStateTo( FightingState ) )
    }
  }

  override def undoStep( game:Game ):Game =
    if game.player1.get.pokemons.contents.isEmpty then
      game.copy(state = InitPlayerState)
    else if game.player2.get.pokemons.contents.nonEmpty then
      copy(state = state, player2 = PokePlayer(game.player2.get.name, PokePack(None)))
    else
      copy(state = state, player1 = PokePlayer(game.player1.get.name, PokePack(None)))
  )
}
