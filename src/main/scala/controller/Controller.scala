package de.htwg.se.pokelite
package controller

import util.{ Command, Event, Observable, P1Event, P2Event, PreEvent, UndoManager }
import model.{ AttackMove, Field, Move, P1State, P2State, PlayerMove, PokeMove, PokemonType, PreState, State, Stateable }

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


