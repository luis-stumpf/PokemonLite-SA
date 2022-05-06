package de.htwg.se.pokelite
package model

trait Move{
  def doStep(field: Field): Field
}

case class PokeMove(pokemon: Pokemon) extends Move{
  override def doStep(field: Field): Field = field.setPokemonTo(pokemon)
}

case class ControlMove() extends Move{
  override def doStep(field: Field): Field = field.setNextTurn()
}

case class PlayerMove(name: String) extends Move{
  override def doStep(field: Field): Field = field.setPlayerNameTo(name)
}
