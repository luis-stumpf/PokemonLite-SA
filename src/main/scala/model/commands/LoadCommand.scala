package model.commands

import de.htwg.se.pokelite.model.GameInterface
import scala.util.Try
import de.htwg.se.pokelite.model.FileIOInterface
import de.htwg.se.pokelite.model.Command
import scala.util.Success
import scala.util.Failure
import de.htwg.se.pokelite.model.CanNotUndoLoad

case class LoadCommand( fileIO: FileIOInterface )
    extends Command[GameInterface]:
  override def doStep( game: GameInterface ): Try[GameInterface] =
    Success( fileIO.load )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Failure( CanNotUndoLoad )
