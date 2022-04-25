package de.htwg.se.pokelite
package model

trait PokemonType{
  val name: String
  val hp: Int
}

case class Pokemon(name: String, hp: Int) extends PokemonType:
  override def toString: String = name + " HP: " + hp
  def changeHp(newHp: Int): Pokemon = copy(hp = newHp)

case class NoPokemon(name: String = "", hp: Int = -1) extends PokemonType:
  override def toString: String = ""
  