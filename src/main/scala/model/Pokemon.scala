package de.htwg.se.pokelite
package model


trait PokemonType{
  val name: String
  val hp: Int
  override def toString: String = name + " HP: " + hp
}

case class NoPokemon(name: String = "", hp: Int = -1) extends PokemonType:
  override def toString: String = ""

case class Glurak(name: String = "Glurak", hp: Int = 150) extends PokemonType {

}

case class Simsala(name: String = "Simsala", hp: Int = 130){

}

case class Brutalanda(name: String = "Brutalanda", hp: Int = 180){

}