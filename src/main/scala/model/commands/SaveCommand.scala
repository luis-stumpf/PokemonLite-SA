package de.htwg.se.pokelite
package model.commands

import de.htwg.se.pokelite.model.GameInterface
import scala.util.Try
import de.htwg.se.pokelite.model.Command
import de.htwg.se.pokelite.model.FileIOInterface
import scala.util.Success
import scala.util.Failure
import de.htwg.se.pokelite.model.CanNotUndoSave

case class SaveCommand( fileIO: FileIOInterface )
    extends Command[GameInterface]:
  override def doStep( game: GameInterface ): Try[GameInterface] =
    fileIO.save( game )
    Success( game )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    Failure( CanNotUndoSave )
