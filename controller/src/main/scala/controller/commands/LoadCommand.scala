package controller.commands

import model.GameInterface
import scala.util.Try
import fileIo.FileIOInterface
import scala.util.Success
import scala.util.Failure
import util.CanNotUndoLoad
import util.Command

case class LoadCommand( fileIO: FileIOInterface )
    extends Command[GameInterface]:
  override def doStep( game: GameInterface ): Try[GameInterface] =
    Success( fileIO.load )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Failure( CanNotUndoLoad )
