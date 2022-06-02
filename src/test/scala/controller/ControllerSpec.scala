package de.htwg.se.pokelite
package controller

import model.{ Field, Game, PokePack, PokePlayer, Pokemon }
import util.Observer

import de.htwg.se.pokelite.model.PokemonType.Glurak
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import util.UndoManager

import de.htwg.se.pokelite.model.commands.ChangeStateCommand
import de.htwg.se.pokelite.model.states.{ InitPlayerState, InitState }

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {

    val controller = Controller()
    "have a Undo Manager" in {
      assert(controller.undoManager.isInstanceOf[UndoManager])
    }

    "have a Game" in {
      assert(controller.game.isInstanceOf[Game])
    }

    "notify its observers on change" in {
      class TestObserver(controller: Controller) extends Observer:
        controller.add(this)
        var bing = false
        def update = bing = true
      val testObserver = TestObserver(controller)
      testObserver.bing should be(false)
      controller.moveDone(controller.game, ChangeStateCommand(InitState(), InitPlayerState()))
      testObserver.bing should be(true)
    }
  }

}
