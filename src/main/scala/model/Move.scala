package de.htwg.se.pokelite
package model

case class PokeMove(pokemons: List[Option[Pokemon]])

case class PlayerMove(name : String)

case class AttackMove(attack : Int) 