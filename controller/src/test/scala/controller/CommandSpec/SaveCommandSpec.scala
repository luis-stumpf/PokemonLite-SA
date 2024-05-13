package controller.CommandSpec

import util.CanNotUndoSave
import model.GameInterface
import fileIo.FileIOInterface
import controller.commands.SaveCommand
import model.impl.game.Game

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success, Try }
import fileIo.json.FileIOJson

class SaveCommandSpec extends AnyWordSpec {
  "SaveCommand" when {
    val newGame = Game()
    val saveCommand = SaveCommand( new FileIOJson() )

    "success" in {
      val res = saveCommand.doStep( newGame ) should be( Success( newGame ) )
    }
  }
}
