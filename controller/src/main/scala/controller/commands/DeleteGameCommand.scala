package controller.commands

import model.GameInterface
import scala.util.Try
import fileIo.FileIOInterface
import scala.util.Success
import scala.util.Failure
import util.CanNotUndoSave
import util.Command

case class DeleteGameCommand( fileIO: FileIOInterface )
    extends Command[GameInterface]:
  override def doStep( game: GameInterface ): Try[GameInterface] =
    fileIO.delete
    Success( game )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Failure( CanNotUndoSave )
