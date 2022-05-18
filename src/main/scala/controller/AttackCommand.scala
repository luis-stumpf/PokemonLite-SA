package de.htwg.se.pokelite
package controller

import model.Field
import model.{ AttackMove, Field }

import de.htwg.se.pokelite.util.{ Command, P1Event, P2Event }

class AttackCommand(move: AttackMove) extends Command[Field]:
  override def noStep(field: Field): Field = field
  override def doStep(field: Field): Field =
    if(field.isControlledBy == 1)
      field.handle(P1Event())
    else
      field.handle(P2Event())
    field.attack(move.attack).setNextTurn()
  override def undoStep(field: Field): Field = field.setNextTurn().attackInv(move.attack)
  override def redoStep(field: Field): Field = field.attack(move.attack).setNextTurn()
