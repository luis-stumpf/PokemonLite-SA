/*
package controller.CommandSpec

import util.CanNotUndoSave
import model.GameInterface
import fileIo.FileIOInterface
import controller.commands.SaveCommand
import model.impl.game.Game

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success, Try }

class SaveCommandSpec extends AnyWordSpec {
  "SaveCommand" when {
    val newGame = Game()
    val fileIOMock: FileIOInterface = new FileIOMock()
    val saveCommand = SaveCommand(fileIOMock)

    "failure" in {
      saveCommand.undoStep(newGame) should be(Failure(CanNotUndoSave))
    }

    "success" in {
      val res = saveCommand.doStep(newGame)
      res shouldBe a[Success[?]]
    }
  }
}

class FileIOMock extends FileIOInterface {
  override def save(game: GameInterface): Unit = {}
  override def load: GameInterface = Game()
}
 */
