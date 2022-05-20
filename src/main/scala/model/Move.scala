package de.htwg.se.pokelite
package model

import scala.util.Failure

trait Move {
  def doStep(field : Field) : Field
}

case class PokeMove(pokemons : List[ Option[ Pokemon ] ]) extends Move {
  override def doStep(field : Field) : Field = field.setPokemonTo( pokemons ).setNextTurn()
}

case class PlayerMove(name : String) extends Move {
  override def doStep(field : Field) : Field = field.setPlayerNameTo( name ).setNextTurn()
}

case class ChangePokeMove(i: Int) extends Move {
  override def doStep(field : Field) : Field = field.changePokemon(i).setNextTurn()
}


case class AttackMove(attack : Int)

object SaveAttackMove {
  def apply(move: AttackMove): AttackMove = {
    if (move.attack <=3 && move.attack >=0) then
      AttackMove(move.attack)
    else throw new IndexOutOfBoundsException("index kaputt")
  }
}
