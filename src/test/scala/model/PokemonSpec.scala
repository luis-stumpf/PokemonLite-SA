package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class PokemonSpec extends AnyWordSpec {
  "A NoPokemon" should {
    val noPokemon = NoPokemon()
    "have a String of form ''" in {
      noPokemon.toString should be("")
      noPokemon.attacks.apply(0) should be(NoAttack())
    }
  }
  "A Glurak" should {
    val pokemon = Glurak()
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be("Glurak HP: 150")
      pokemon.attacks.apply(0) should be(Attack("Flammenwurf", 30))
    }
  }
  "A Simsala" should {
    val pokemon = Simsala()
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be("Simsala HP: 130")
      pokemon.attacks.apply(0) should be(Attack("Simsala", 30))
    }
  }
  "A Brutalanda" should {
    val pokemon = Brutalanda()
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be("Brutalanda HP: 180")
      pokemon.attacks.apply(0) should be(Attack("Flammenwurf", 30))
    }
  }
}
