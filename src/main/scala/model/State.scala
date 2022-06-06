package de.htwg.se.pokelite
package model

trait State {

  def initPlayers() : Option[ Command ] = None
  def addPlayer(name: String): Option[Command] = None
  def addPokemons(name : String) : Option[ Command ] = None
  def attackWith(input: String) : Option[Command] = None
  def switchPokemon(input: String): Option[Command] = None
  def nextMove(input: String): Option[Command] = None
  def switchPokemonTo(input: String): Option[Command] = None
  def restartTheGame():Option[Command] = None

}

