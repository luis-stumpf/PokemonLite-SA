package de.htwg.se.pokelite
package controller

import util.{Observable, Command, UndoManager}
import model.{Field, PlayerMove, PokeMove, AttackMove, PokemonType}

case class Controller(var field: Field) extends Observable:

  val undoManager = new UndoManager[Field]
  override def toString: String = field.toString

  def doAndPublish(doThis: PokeMove => Field, move: PokeMove) =
    field = doThis(move)
    notifyObservers

  def doAndPublish(doThis: PlayerMove => Field, move: PlayerMove) =
    field = doThis(move)
    notifyObservers

  def doAndPublish(doThis: AttackMove => Field, move: AttackMove) =
    field = doThis(move)
    notifyObservers

  def putPoke(move: PokeMove): Field = undoManager.doStep(field, PokeCommand(move))
  def putPlayer(move: PlayerMove): Field = undoManager.doStep(field, PlayerCommand(move))
  def putAttack(move: AttackMove): Field = undoManager.doStep(field, AttackCommand(move))
  def undo: Field = undoManager.undoStep(field)
  def redo: Field = undoManager.redoStep(field)
  
  //def put(move: Move): Field = move.doStep(field)