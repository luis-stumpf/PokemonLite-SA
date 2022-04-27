package de.htwg.se.pokelite.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      val field = new Field(50, "", "", NoPokemon(), NoPokemon())
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
      val field = new Field(50, "Luis", "Luis", NoPokemon(), NoPokemon())
      val attackList = List(Attack("Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite",15), Attack("Tackle", 10))

      "calc space Int" in {
        field.calcSpace(0.9) should be(45)
        field.calcSpace(0.9, "Luis") should be(41)
      }
      "have a mesh in form of \n" +
      "+----------------+----------------+\n" +
      "|          Luis  |                |\n" +
      "|                |                |\n" +
      "|                |                |\n" +
      "|                |                |\n" +
      "|  Luis          |                |\n" +
      "+----------------+----------------+\n" in {
        field.setNameP1("Luis").setNameP2("Luis").setPokemonP1(Pokemon("Glurak", 150, attackList)).setPokemonP2(Pokemon("Simsala", 130, attackList)).mesh() should be(
            "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 150     |     Flammenwurf         Donnerblitz              |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 130                              |     Bite                Tackle                   |\n"+
            "|     Luis                                         |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
  }
}