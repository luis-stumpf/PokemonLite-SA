package controller.CommandSpec

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.BeforeAndAfterEach
import model.impl.game.Game
import controller.commands.GameOverCommand
import org.scalatest.matchers.should.Matchers.*
import scala.util.Success

class GameOverCommandSpec extends AnyWordSpec with BeforeAndAfterEach {

  val oldGame = Game()

  "GameOverCommand" when {
    "return a new Game" in {
      GameOverCommand( oldGame, oldGame.state ).doStep( oldGame ) should be(
        Success( Game() )
      )
    }
  }
}
