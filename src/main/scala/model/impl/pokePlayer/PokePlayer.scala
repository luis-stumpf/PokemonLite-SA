package de.htwg.se.pokelite
package model.impl.pokePlayer

import model.{ GameInterface, PokePack, PokePlayerInterface, Pokemon, PokemonArt }
import model.impl.pokePlayer

import de.htwg.se.pokelite.model.impl.game.Game

case class PokePlayer(name : String, pokemons : PokePack = PokePack( List( None ) ), currentPoke : Int = 0) extends PokePlayerInterface :

  override def toString = name

  def setPokemonTo(newPokemons : PokePack) : PokePlayer = copy( pokemons = newPokemons )

  def setPokePlayerNameTo(newName : String) : PokePlayer = copy( name = newName )

  def setCurrentPokeTo(number : Int) : PokePlayer = copy( currentPoke = number - 1 )

  def checkForDead() : Boolean = pokemons.contents.take( Game.maxPokePackSize ).forall( x => x.get.isDead )

  def getCurrentPokemonType : PokemonArt = pokemons.contents.apply( currentPoke ).get.pType.pokemonArt

  def getCurrentPokemon : Pokemon = pokemons.contents.apply( currentPoke ).get

  def currentPokemonDamageWith(attackNumber : Int) : Int = pokemons.contents.apply( currentPoke ).get.damageOf( attackNumber )

  def reduceHealthOfCurrentPokemon(amount : Double) =
    copy( pokemons = PokePack(pokemons.contents.updated( currentPoke, Some(getCurrentPokemon.reduceHP( amount ) ) )))

  def increaseHealthOfCurrentPokemon(amount : Double) =
    copy( pokemons = PokePack(pokemons.contents.updated( currentPoke, Some(getCurrentPokemon.increaseHP( amount ) ) )))



