package de.htwg.se.pokelite
package controller

import util.{ Command, Observable, UndoManager }
import model.{ AttackMove, Field, PlayerMove, PokeMove, Move, PokemonType }

case class Controller(var field: Field) extends Observable:

  val undoManager = new UndoManager[Field]
  override def toString: String = field.toString

  def doAndPublish(doThis: AttackMove => Field, move: AttackMove) =
    field = doThis(move)
    notifyObservers

  def doAndPublish(doThis: => Field) =
    field = doThis
    notifyObservers

  def doAndPublish(doThis: Move => Field, move: Move) =
    field = doThis(move)
    notifyObservers
    
  def put(move: Move): Field = move.doStep(field)
  def putAttack(move: AttackMove): Field = undoManager.doStep(field, AttackCommand(move))
  def undo: Field = undoManager.undoStep(field)
  def redo: Field = undoManager.redoStep(field)
  
  //def put(move: Move): Field = move.doStep(field)