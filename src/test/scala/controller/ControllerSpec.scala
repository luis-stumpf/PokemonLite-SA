package de.htwg.se.pokelite
package controller

import model.{ Field, Move, PlayerMove, PokeMove, PokePlayer, Pokemon }
import util.Observer

import de.htwg.se.pokelite.model.PokemonType.Glurak
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {
    val controller = Controller(new Field(50, PokePlayer("", 1), PokePlayer("", 2)))
    "put a name in field" in {
      val fieldWithName = controller.put(PlayerMove(name = "Luis"))
      fieldWithName.player1.name should be("Luis")
    }
    "set Pokemon" in {
      val pokeList1 = List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) )
      val fieldWithPokemon = controller.put(PokeMove(pokemons = pokeList1))
      fieldWithPokemon.player1.pokemons should be(List(Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) ))
    }
    "doAndPublisch" in {
      controller.doAndPublish(controller.put, PlayerMove(""))
      controller.field.player1.name should be("")
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
      controller.doAndPublish(controller.put, PlayerMove(""))
      testObserver.bing should be(true)
    }
  }

}
