package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MoveSpec extends AnyWordSpec {
  "A Move" should {
    "have a name and Pokemon." in {
      val move = Move()
      move.name should be("")
      move.pokemon should be(NoPokemon())
    }
    "filled Move" in{
      val move = Move("Luis", Glurak())
      move.name should be("Luis")
      move.pokemon should be(Glurak())
    }
  }
}
