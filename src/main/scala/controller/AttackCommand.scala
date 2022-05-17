package de.htwg.se.pokelite
package controller

import model.Field
import model.{ Field, AttackMove }
import de.htwg.se.pokelite.util.Command

class AttackCommand(move: AttackMove) extends Command[Field]:
  override def noStep(field: Field): Field = field
  override def doStep(field: Field): Field = field.attack(move.attack)
  override def undoStep(field: Field): Field = field.attackInv(move.attack)
  override def redoStep(field: Field): Field = field.attack(move.attack)
