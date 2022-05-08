package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class PokePlayerSpec extends AnyWordSpec {
  "PokePlayerSpec" should {
    val p1 = PokePlayer("Luis", 1, NoPokemon())
    val p2 = PokePlayer("Timmy", 1, NoPokemon())
    val p3 = PokePlayer("Otto", 0, NoPokemon())
    "have a name in form 'name'" in {
      p1.toString should be("Luis")
      p2.toString should be("Timmy")
      p3.toString should be("Otto")
    }
    "set Pokemon in form new PokePlayer" in {
      p1.setPokemonTo(Glurak()).pokemon should be(Glurak())
    }
    "set Player Name in form new PokePlayer" in {
      p1.setPokePlayerNameTo("Udo").name should be("Udo")
    }
  }
}
