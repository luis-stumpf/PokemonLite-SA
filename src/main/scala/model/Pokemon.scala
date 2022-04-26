package de.htwg.se.pokelite
package model


trait PokemonType{
  val name: String
  val hp: Int
  val attacks: List[AttackType]
}

case class Pokemon(name: String, hp: Int, attacks: List[Attack]) extends PokemonType:
  override def toString: String = name + " HP: " + hp
  def changeHp(newHp: Int): Pokemon = copy(hp = newHp)

case class NoPokemon(name: String = "", hp: Int = -1, attacks: List[NoAttack] = List(NoAttack())) extends PokemonType:
  override def toString: String = ""
  