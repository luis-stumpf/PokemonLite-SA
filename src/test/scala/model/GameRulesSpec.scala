package de.htwg.se.pokelite
package model

import model.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameRulesSpec extends AnyWordSpec {
  "The Game Rules" should {
    "return a damage Multiplikator with water and water" in {
      getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Wasser) should be(1)
    }
    "return a damage Multiplikator with water and feuer" in {
      getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Feuer) should be(1.2)
    }
    "return a damage Multiplikator with water and blatt" in {
      getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Blatt) should be(0.5)
    }
    "return a damage Multiplikator with water and Psycho" in {
      getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Psycho) should be(1)
    }
  }

}
