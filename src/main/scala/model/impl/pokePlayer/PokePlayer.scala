package de.htwg.se.pokelite
package model.impl.pokePlayer

import model.Pokemon
import model.impl.pokePlayer
import model.PokePack
import model.GameInterface
import model.PokePlayerInterface

import de.htwg.se.pokelite.model.impl.game.Game

case class PokePlayer(name : String, pokemons : PokePack[Option[Pokemon]] = PokePack(List( None )), currentPoke : Int = 0) extends PokePlayerInterface:

  override def toString = name


  def setPokemonTo(newPokemons : PokePack[ Option[ Pokemon ] ]) : PokePlayer = copy( pokemons = newPokemons )

  def setPokePlayerNameTo(newName : String) : PokePlayer = copy( name = newName )

  def setCurrentPokeTo(number: Int): PokePlayer = copy(currentPoke = number - 1)

  def checkForDead() : Boolean = pokemons.contents.take(Game.pokePackSize).forall(x => x.get.isDead )




