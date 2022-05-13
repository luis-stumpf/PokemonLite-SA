package de.htwg.se.pokelite.model

import de.htwg.se.pokelite.model.PokemonType.{ Glurak, Simsala }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      val field = new Field(50, PokePlayer("",1), PokePlayer("",2))
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
      val field = Field(50, PokePlayer("Luis", 1), PokePlayer("Luis", 2))

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
        field.setPlayerNameTo("Luis").setPlayerNameTo("Timmy").setPokemonTo(List(Some(Pokemon(Glurak)))).setPokemonTo(List(Some(Pokemon(Simsala)))).mesh() should be(
            "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 150     |     1. Flammenwurf      2. Donnerblitz           |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 130                              |     3. Bite             4. Tackle                |\n"+
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
        field.setPlayerNameTo("Luis").setPlayerNameTo("Timmy").setPokemonTo(List(Some(Pokemon(Glurak)))).setPokemonTo(List(Some(Pokemon(Simsala)))).setNextTurn().mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
          "|                                         Luis     |                                                  |\n"+
          "|                               Glurak HP: 150     |     1. Simsala          2. Simsala               |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|     Simsala HP: 130                              |     3. Simsala          4. Simsala               |\n"+
          "|     Timmy                                        |                                                  |\n"+
          "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      val newField = Field(50, PokePlayer("Luis",1, List(Some(Pokemon(Glurak)))), PokePlayer("Timmy", 2, List(Some(Pokemon(Simsala))))).setNextTurn().attack(0)
      "attack" in{
        newField.toString should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 120     |     1. Simsala          2. Simsala               |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 130                              |     3. Simsala          4. Simsala               |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "next attack" in{
        newField.setNextTurn().attack(2).mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 120     |     1. Flammenwurf      2. Donnerblitz           |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 115                              |     3. Bite             4. Tackle                |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
  }
}