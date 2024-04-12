package controller.commands

import model.GameInterface
import scala.util.Try
import model.FileIOInterface
import scala.util.Success
import scala.util.Failure
import util.CanNotUndoSave
import util.Command

case class SaveCommand( fileIO: FileIOInterface )
    extends Command[GameInterface]:
  override def doStep( game: GameInterface ): Try[GameInterface] =
    fileIO.save( game )
    Success( game )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Failure( CanNotUndoSave )
