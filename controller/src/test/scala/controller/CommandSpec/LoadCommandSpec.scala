package controller.CommandSpec

import util.CanNotUndoSave
import model.GameInterface
import controller.commands.LoadCommand
import model.impl.game.Game
import fileIo.FileIOInterface

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success}

class LoadCommandSpec extends AnyWordSpec {
  "LoadCommand" when {
    val newGame = Game()
    val fileIOMock: FileIOInterface = new FileIOMock2()
    val loadCommand = LoadCommand(fileIOMock)

    /*"failure" in {
      loadCommand.undoStep(newGame) should be(Failure(CanNotUndoSave))
    }*/

    "success" in {
      val loadedGame = loadCommand.doStep(newGame)
      loadedGame shouldBe a[Success[?]]
      loadedGame.get shouldEqual fileIOMock.load
    }
  }
}

class FileIOMock2 extends FileIOInterface {
  override def save(game: GameInterface): Unit = {}
  override def load: GameInterface = Game()
}