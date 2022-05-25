package de.htwg.se.pokelite
package controller

import util._
import model._

case class Controller(var field : Field) extends Observable, Stateable, Event :

  val undoManager = new UndoManager[ Field ]

  override def toString : String = field.toString

  def doAndPublish(doThis : AttackMove => Field, move : AttackMove) =
    field = doThis( move )
    notifyObservers(MidEvent())

  def doAndPublish(doThis : => Field) =
    field = doThis
    notifyObservers(PreEvent())

  def doAndPublish(doThis : Move => Field, move : Move) =
    field = doThis( move )
    notifyObservers(MidEvent())

  def put(move : Move) : Field = move.doStep( field )

  def putAttack(move : AttackMove) : Field = undoManager.doStep( field, AttackCommand( move ) )

  def undo : Field = undoManager.undoStep( field )

  def redo : Field = undoManager.redoStep( field )


  override def handle(e : Event) : Option[ State ] =
    e match {
      case pre : PreEvent => state = Some( PreState( field ) )
      case mid : MidEvent => state = Some( MidState( field ) )
      case end : EndEvent => state = Some( EndState( field ) )
    }
    state

  



