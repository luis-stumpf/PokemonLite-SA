package de.htwg.se.pokelite
package model

import model.Pokemon


case class PokePlayer(name : String, number : Int, pokemon : Option[ Pokemon ] = None):
  override def toString = name

  def setPokemonTo(newPokemon : Pokemon) : PokePlayer = copy( pokemon = Some( newPokemon ) )

  def setPokePlayerNameTo(newName : String) : PokePlayer = copy( name = newName )