package util

import util.{ NothingToRedo, NothingToUndo }

import scala.util.{ Failure, Success, Try }

trait Command[T]:

  def doStep( game: T ): Try[T]

  def undoStep( game: T ): Try[T]

class UndoManager[T]:
  private var undoStack: List[Command[T]] = Nil
  private var redoStack: List[Command[T]] = Nil

  def doStep( t: T, command: Command[T] ): Try[T] =
    undoStack = command :: undoStack
    command.doStep( t )

  def undoStep( t: T ): Try[T] =
    undoStack match {
      case Nil => Failure( NothingToUndo )
      case head :: stack => {
        val result = head.undoStep( t )
        undoStack = stack
        redoStack = head :: redoStack
        result
      }
    }

  def redoStep( t: T ): Try[T] =
    redoStack match {
      case Nil => Failure( NothingToRedo )
      case head :: stack => {
        val result = head.doStep( t )
        redoStack = stack
        undoStack = head :: undoStack
        result
      }
    }

  def lastCommand: Option[Command[T]] = undoStack.headOption
