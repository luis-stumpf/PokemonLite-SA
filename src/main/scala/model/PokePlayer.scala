package de.htwg.se.pokelite
package model

import model.{Pokemon, Game}

case class PokePlayer(name : String, pokemons : PokePack[Option[Pokemon]] = PokePack(List( None )), currentPoke : Int = 0):
  override def toString = name

  def setPokemonTo(newPokemons : PokePack[ Option[ Pokemon ] ]) : PokePlayer = copy( pokemons = newPokemons )

  def setPokePlayerNameTo(newName : String) : PokePlayer = copy( name = newName )

  def checkForDead() : Boolean = pokemons.contents.take(Game.pokePackSize).forall( x => x.get.isDead )




