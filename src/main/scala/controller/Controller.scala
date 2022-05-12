package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, Move, PlayerMove, PokeMove, PokemonType}

case class Controller(var field: Field) extends Observable:
  override def toString: String = field.toString
  def doAndPublish(doThis: Move => Field = put, move: Move) =
    field = doThis(move)
    notifyObservers
  def put(move: Move): Field = move.doStep(field)