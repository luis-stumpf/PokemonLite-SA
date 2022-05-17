package de.htwg.se.pokelite
package model

trait Move {
  def doStep(field : Field) : Field
}

case class PokeMove(pokemons: List[Option[Pokemon]]) extends Move {
  override def doStep(field : Field) : Field = field.setPokemonTo( pokemons ).setNextTurn()
}

case class PlayerMove(name : String) extends Move {
  override def doStep(field : Field) : Field = field.setPlayerNameTo( name ).setNextTurn()
}


case class AttackMove(attack : Int) 