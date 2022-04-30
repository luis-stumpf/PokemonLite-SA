package de.htwg.se.pokelite
package controller

import model.{Field, Glurak, NoPokemon, PokePlayer, Move}
import util.Observer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {
    val controller = Controller(new Field(50, PokePlayer("", 1, NoPokemon()), PokePlayer("", 2, NoPokemon())))
    "put a name in field" in {
      val fieldWithName = controller.setPlayerNameTo(Move(name = "Luis"))
      fieldWithName.player1.name should be("Luis")
    }
    "set Pokemon" in {
      val fieldWithPokemon = controller.setPokemonTo(Move(pokemon = Glurak()))
      fieldWithPokemon.player1.pokemon should be(Glurak())
    }
    "field controlled by next Player" in {
      val fieldControlledNext = controller.giveControlToNextPlayer(Move())
      fieldControlledNext.isControlledBy should be(2)
    }
    "have a string" in {
      controller.toString should be(
        "+--------------------------------------------------+--------------------------------------------------+\n"+
        "|                                                  |                                                  |\n"+
        "|                                                  |                                                  |\n"+
        "|                                                  |                                                  |\n"+
        "|                                                  |                                                  |\n"+
        "|                                                  |                                                  |\n"+
        "|                                                  |                                                  |\n"+
        "|                                                  |                                                  |\n"+
        "+--------------------------------------------------+--------------------------------------------------+\n"
      )
    }
    "notify its observers on change" in {
      class TestObserver(controller: Controller) extends Observer:
        controller.add(this)
        var bing = false
        def update = bing = true
      val testObserver = TestObserver(controller)
      testObserver.bing should be(false)
      controller.doAndPublish(controller.giveControlToNextPlayer, Move())
      testObserver.bing should be(true)
    }
  }

}
