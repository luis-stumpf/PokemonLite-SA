package de.htwg.se.pokelite
package util

import model.{ Command, GameInterface, NothingToRedo, NothingToUndo }

import scala.util.{ Failure, Success, Try }

class UndoManager:
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep( command: Command ): Unit =
    undoStack = command :: undoStack

  def undoStep(): Try[Command] =
    undoStack match {
      case Nil => Failure( NothingToUndo )
      case head :: stack => {
        val result = head
        undoStack = stack
        redoStack = head :: redoStack
        Success( result )
      }
    }

  def redoStep(): Try[Command] =
    redoStack match {
      case Nil => Failure( NothingToRedo )
      case head :: stack => {
        val result = head
        redoStack = stack
        undoStack = head :: undoStack
        Success( result )
      }
    }
