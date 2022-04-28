package de.htwg.se.pokelite
package model


trait PokemonType{
  val name: String
  val hp: Int
  val attacks: List[AttackType]
  override def toString: String = name + " HP: " + hp
}

case class NoPokemon(name: String = "", hp: Int = -1) extends PokemonType {
  val attacks = List(NoAttack(), NoAttack(), NoAttack(), NoAttack())
  override def toString: String = ""
}

case class Glurak(name: String = "Glurak", hp: Int = 150) extends PokemonType {
  val attacks = List(Attack("Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite",15), Attack("Tackle", 10))
  

}

case class Simsala(name: String = "Simsala", hp: Int = 130) extends PokemonType {
  val attacks = List(Attack("Simsala", 30), Attack("Simsala", 20), Attack("Simsala",15), Attack("Simsala", 10))

}

case class Brutalanda(name: String = "Brutalanda", hp: Int = 180) extends PokemonType {
  val attacks = List(Attack("Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite",15), Attack("Tackle", 10))

}