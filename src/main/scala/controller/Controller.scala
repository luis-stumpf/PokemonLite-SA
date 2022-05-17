package de.htwg.se.pokelite
package controller

import util.{Observable, Command, UndoManager}
import model.{Field, Move, PlayerMove, PokeMove, PokemonType}

case class Controller(var field: Field) extends Observable:

  val undoManager = new UndoManager[Field]
  override def toString: String = field.toString

  def doAndPublish(doThis: Move => Field = put, move: Move) =
    field = doThis(move)
    notifyObservers

  def put(move: Move): Field = undoManager.doStep(field, PutCommand(move))
  def undo: Field = undoManager.undoStep(field)
  def redo: Field = undoManager.redoStep(field)
  
  //def put(move: Move): Field = move.doStep(field)