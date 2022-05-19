package de.htwg.se.pokelite
package controller

import model.*
import util.{ Observer, PreEvent, MidEvent, EndEvent }

import de.htwg.se.pokelite.model.PokemonType.{ Brutalanda, Glurak, Simsala, Turtok }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {
    val controller = Controller( new Field( 50, PokePlayer( "", 1 ), PokePlayer( "", 2 ) ) )
    "put a name in field" in {
      val fieldWithName = controller.put( PlayerMove( name = "Luis" ) )
      fieldWithName.player1.name should be( "Luis" )
    }
    "set Pokemon" in {
      val pokeList1 = List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) )
      val fieldWithPokemon = controller.put( PokeMove( pokemons = pokeList1 ) )
      fieldWithPokemon.player1.pokemons should be( PokePack( List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) ) ) )
    }
    "doAndPublisch" in {
      controller.doAndPublish( controller.put, PlayerMove( "" ) )
      controller.field.player1.name should be( "" )
    }
    "have a string" in {
      controller.toString should be(
        "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n"
      )
    }
    "handle event" in {
      controller.handle(PreEvent()).get.toString should be("PreGame!\n")
      controller.handle(MidEvent()).get.toString should be("MidGame!\n")
      controller.handle(EndEvent()).get.toString should be("EndGame!\n")
    }
    "doandpublish attackmove" in {
      val controllerFilled = Controller( Field( 50,
        PokePlayer( "", 1, PokePack( List( Some( Pokemon( Glurak ) ), Some( Pokemon( Simsala ) ) ) ) ),
        PokePlayer( "", 2, PokePack( List( Some( Pokemon( Turtok ) ), Some( Pokemon( Brutalanda ) ) ) ) ) ) )
      controllerFilled.doAndPublish(controllerFilled.putAttack, AttackMove(0))
      controllerFilled.field.player1.pokemons.contents.apply(0).get.hp should be(150)
    }

    "notify its observers on change" in {
      class TestObserver(controller : Controller) extends Observer :
        controller.add( this )
        var bing = false

        def update = bing = true
      val testObserver = TestObserver( controller )
      testObserver.bing should be( false )
      controller.doAndPublish( controller.put, PlayerMove( "" ) )
      testObserver.bing should be( true )
    }
  }

}
