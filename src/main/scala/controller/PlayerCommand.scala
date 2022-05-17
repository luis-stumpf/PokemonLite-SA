package de.htwg.se.pokelite
package controller

import model.{ Field, PlayerMove }
import util.Command

class PlayerCommand(move: PlayerMove) extends Command[Field]:
  override def noStep(field: Field): Field = field
  override def doStep(field: Field): Field = field.setPlayerNameTo(move.name)
  override def undoStep(field: Field): Field = field.setPlayerNameTo("")
  override def redoStep(field: Field): Field = field.setPlayerNameTo(move.name)
