package de.htwg.se.pokelite
package controller.commands

import de.htwg.se.pokelite.model.GameInterface
import scala.util.Try
import de.htwg.se.pokelite.model.FileIOInterface
import scala.util.Success
import scala.util.Failure
import de.htwg.se.pokelite.model.CanNotUndoLoad
import de.htwg.se.pokelite.util.Command

case class LoadCommand( fileIO: FileIOInterface )
    extends Command[GameInterface]:
  override def doStep( game: GameInterface ): Try[GameInterface] =
    Success( fileIO.load )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Failure( CanNotUndoLoad )
