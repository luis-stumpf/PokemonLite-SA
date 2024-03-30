package de.htwg.se.pokelite
package model.impl.field

import model.PokemonType.{ Glurak, Simsala, Turtok }
import model.impl.pokePlayer.PokePlayer
import model.{ PokePack, Pokemon }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      val field = Field( 50, PokePlayer( "" ), PokePlayer( "" ) )
      "have a bar as String of form '+---+---+'" in {
        field.row() should be( "+--------------------------------------------------+--------------------------------------------------+\n" )
      }
      "have a cell as String of form '|  |  |'" in {
        field.col( 1 ) should be( "|                                                  |                                                  |\n" )
      }
      "have a scalable height" in {
        field.col( 1 ) should be( "|                                                  |                                                  |\n" )
        field.col( 2 ) should be( "|                                                  |                                                  |\n" * 2 )
        field.col( 3 ) should be( "|                                                  |                                                  |\n" * 3 )
        field.col( 4 ) should be( "|                                                  |                                                  |\n" * 4 )

      }
      "have a mesh in the form " +
        "+-+-+" +
        "| | |" +
        "+-+-+" in {
        field.mesh( 1 ) should be(
          "+--------------------------------------------------+--------------------------------------------------+\n" +
            "|                                                  |                                                  |\n" +
            "|                                                  |                                                  |\n" +
            "|                                                  |                                                  |\n" +
            "|                                                  |                                                  |\n" +
            "|                                                  |                                                  |\n" +
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
    "full" should {
      val field = Field( 50, PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ), PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) )

      "print the attacks of current pokemon" in {
        field.toString should be(
          "+--------------------------------------------------+--------------------------------------------------+\n" +
            "|                                         Luis     |                                                  |\n" +
            "|                               Glurak HP: 150     |     1. Glut                  2. Flammenwurf      |\n" +
            "|                                                  |                                                  |\n" +
            "|                                                  |                                                  |\n" +
            "|                                                  |                                                  |\n" +
            "|     Simsala HP: 130                              |     3. Biss                  4. Inferno          |\n" +
            "|     Timmy                                        |                                                  |\n" +
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
  }
}