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
      "have a scalable cell" in {
        col(9, 1) should be("|         |         |\n")
        col(11, 1) should be("|           |           |\n")
        col(10, 2) should be("|          |          |\n"*2)
      }
      "have a scalable bar" in {
        row(1) should be("+-+-+\n")
        row(2) should be("+--+--+\n")
        row(3) should be("+---+---+\n")
      }
      "have a mesh in the form " +
        "+-+-+" +
        "| | |" +
        "+-+-+" in {
        mesh(1, 1) should be("+-+-+\n" + "| | |\n" + "+-+-+\n")
      }
    }
  }
}