package de.htwg.se.pokelite.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      "have a bar as String of form '+---+---+'" in {
        row(10) should be("+----------+----------+\n")
      }
      "have a cell as String of form '|  |  |'" in {
        col(10, 1) should be("|          |          |\n")
      }
      "have a scalable height" in {
        col(8, 1) should be("|        |        |\n")
        col(11, 1) should be("|           |           |\n")
        col(10, 2) should be("|          |          |\n"*2)
      }
      "have a scalable width" in {
        row(1) should be("+-+-+\n")
        row(2) should be("+--+--+\n")
        row(3) should be("+---+---+\n")
      }
      "have a mesh in the form " +
        "+-+-+" +
        "| | |" +
        "+-+-+" in {
        mesh(50, 1, "", "") should be(
          "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
    "with input" should {
      "have a printPlayer1 form of '|      name  |         |'" in {
        printPlayer1(60, "Luis") should be(
          "|                                        Luis                |                                                            |\n"
        )
      }
      "have a printPlayer2 in form of '|   name       |          |'" in {
        printPlayer2(60, "Luis") should be(
          "|          Luis                                              |                                                            |\n"
        )
      }
      "have a mesh in form of \n" +
      "+----------------+----------------+\n" +
      "|          Luis  |                +\n" +
      "|                |                +\n" +
      "|  Luis          |                +\n" +
      "+----------------+----------------+\n" in {
        mesh(60, 1, "Luis", "Luis") should be(
          "+------------------------------------------------------------+------------------------------------------------------------+\n" +
          "|                                        Luis                |                                                            |\n" +
          "|                                                            |                                                            |\n" +
          "|          Luis                                              |                                                            |\n" +
          "+------------------------------------------------------------+------------------------------------------------------------+\n"
        )
      }

    }
  }
}