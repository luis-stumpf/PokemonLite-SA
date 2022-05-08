package de.htwg.se.pokelite.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      val field = new Field(50, PokePlayer("",1,NoPokemon()), PokePlayer("",2,NoPokemon()))
      "have a bar as String of form '+---+---+'" in {
        field.row() should be("+--------------------------------------------------+--------------------------------------------------+\n")
      }
      "have a cell as String of form '|  |  |'" in {
        field.col( 1) should be("|                                                  |                                                  |\n")
      }
      "have a scalable height" in {
        field.col(1) should be("|                                                  |                                                  |\n")
        field.col(2) should be("|                                                  |                                                  |\n"*2)
        field.col(3) should be("|                                                  |                                                  |\n"*3)
        field.col(4) should be("|                                                  |                                                  |\n"*4)

      }
      "have a mesh in the form " +
        "+-+-+" +
        "| | |" +
        "+-+-+" in {
        field.mesh(1) should be(
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
    "with input" should {
      val field = Field(50, PokePlayer("Luis", 1, NoPokemon()), PokePlayer("Luis", 2, NoPokemon()))

      "calc space Int" in {
        field.calcSpace(0.9) should be(45)
        field.calcSpace(0.9, "Luis") should be(41)
      }
      "Field state '1 or 2'" in{
        field.isControlledBy should be(1)
        field.setNextTurn().isControlledBy should be(2)
      }
      "have a mesh in form of \n" +
      "+----------------+----------------+\n" +
      "|          Luis  |                |\n" +
      "|         poke   |  0        1    |\n" +
      "|                |                |\n" +
      "|  poke          |  2        3    |\n" +
      "|  Luis          |                |\n" +
      "+----------------+----------------+\n" in {
        field.setPlayerNameTo("Luis").setPlayerNameTo("Timmy").setPokemonTo(Glurak()).setPokemonTo(Simsala()).mesh() should be(
            "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 150     |     Flammenwurf         Donnerblitz              |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 130                              |     Bite                Tackle                   |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "have a mesh at next step\n" +
        "+----------------+----------------+\n" +
        "|          Luis  |                |\n" +
        "|         poke   |  0        1    |\n" +
        "|                |                |\n" +
        "|  poke          |  2        3    |\n" +
        "|  Luis          |                |\n" in {
        field.setPlayerNameTo("Luis").setPlayerNameTo("Timmy").setPokemonTo(Glurak()).setPokemonTo(Simsala()).setNextTurn().mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
          "|                                         Luis     |                                                  |\n"+
          "|                               Glurak HP: 150     |     Simsala             Simsala                  |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|     Simsala HP: 130                              |     Simsala             Simsala                  |\n"+
          "|     Timmy                                        |                                                  |\n"+
          "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
  }
}