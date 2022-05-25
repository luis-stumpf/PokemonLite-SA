package de.htwg.se.pokelite
package util

import de.htwg.se.pokelite.model.{ Command, Game, NothingToUndo }

import scala.util.{ Failure, Success, Try }

class UndoManager:
  private var undoStack : List[ Command] = Nil
  private var redoStack : List[ Command] = Nil

  def doStep(game : Game, command : Command) : Unit =
    undoStack = command :: undoStack
    command.doStep( game )

  def undoStep() : Try[Command] =
    undoStack match {
      // TODO: Anstatt None, Fehlerbehandlung
      case Nil => Failure( NothingToUndo )
      case head :: stack => {
        val result = head
        undoStack = stack
        redoStack = head :: redoStack
        Success(result)
      }
    }

  def redoStep() : Try[Command] =
    redoStack match {
      // TODO: Anstatt None, Fehlerbehandlung
      case Nil =>  Failure(NothingToRedo)
      case head :: stack => {
        val result = head
        redoStack = stack
        undoStack = head :: undoStack
        Success(result)
      }
    }