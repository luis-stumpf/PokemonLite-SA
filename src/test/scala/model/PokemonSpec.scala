package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.PokemonType.Glurak
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PokemonSpec extends AnyWordSpec {
  "A Pokemon" should {
    val pokemon = Pokemon(Glurak)
    "have a String of form ''" in {
      pokemon.toString should be("Glurak HP: 150")
      pokemon.reduceHP(0) should be(Pokemon(Glurak, 150))
      pokemon.reduceHP(150).toString should be("Glurak is dead")
    }
    "attackInv" in {
      pokemon.increaseHP(20.0).toString should be("Glurak HP: 150")
    }
    "get its HP" in {
      pokemon.getHp should be(150)
    }
  }
  "A Glurak" should {
    val pokemon = PokemonType.Glurak
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be("Glurak HP: 150")
      pokemon.attacks.apply(0) should be(Attack("Glut", 20))
    }
  }
  "A Simsala" should {
    val pokemon = PokemonType.Simsala
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be("Simsala HP: 130")
      pokemon.attacks.apply(0) should be(Attack("Konfusion", 10))
    }
  }
  "A Brutalanda" should {
    val pokemon = PokemonType.Brutalanda
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be("Brutalanda HP: 170")
      Pokemon.toString should be("Pokemon")
    }
  }
}