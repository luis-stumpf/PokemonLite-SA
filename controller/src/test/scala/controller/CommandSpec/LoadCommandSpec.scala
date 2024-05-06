package controller.CommandSpec

import util.CanNotUndoSave
import model.GameInterface
import controller.commands.LoadCommand
import model.impl.game.Game
import fileIo.FileIOInterface

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success }
import fileIo.json.FileIOJson
import controller.commands.SaveCommand

class LoadCommandSpec extends AnyWordSpec {
  "LoadCommand" when {
    var newGame: GameInterface = Game()
    val loadCommand = LoadCommand( new FileIOJson() )

    val saveCommand = SaveCommand( new FileIOJson() )

    newGame = saveCommand.doStep( newGame ).get

    "success" in {
      val loadedGame =
        loadCommand.doStep( newGame ) should be( Success( newGame ) )
    }
  }
}
