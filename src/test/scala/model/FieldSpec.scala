package de.htwg.se.pokelite.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      "have a bar as String of form '+---+---+'" in {
        row() should be("+------------------------------------------------------------+------------------------------------------------------------+\n")
      }
      "have a cell as String of form '|  |  |'" in {
        col() should be("|                                                            |                                                            |\n")
      }
    }
  }
}