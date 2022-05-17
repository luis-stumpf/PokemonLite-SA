package de.htwg.se.pokelite
package controller


import model.{ Field, PokeMove }
import util.Command
import util.UndoManager

class PokeCommand(move: PokeMove) extends Command[Field]:
  override def noStep(field: Field): Field = field
  override def doStep(field: Field): Field = field.setPokemonTo(move.pokemons)
  override def undoStep(field: Field): Field = field.setPokemonTo(List(None))
  override def redoStep(field: Field): Field = field.setPokemonTo(move.pokemons)