package de.htwg.se.pokelite
package model


case class PokePlayer(name: String, number: Int, pokemon: PokemonType):
  override def toString = name

  def setPokemonTo(newPokemon: PokemonType): PokePlayer = copy(pokemon = newPokemon)
  def setPokePlayerNameTo(newName: String): PokePlayer = copy(name = newName)
  //test