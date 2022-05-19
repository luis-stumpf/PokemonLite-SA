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

    "return a damage Multiplikator with fire and water" in {
      getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Wasser) should be(0.5)
    }
    "return a damage Multiplikator with fire and feuer" in {
      getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Feuer) should be(1)
    }
    "return a damage Multiplikator with fire and blatt" in {
      getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Blatt) should be(1.3)
    }
    "return a damage Multiplikator with fire and Psycho" in {
      getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Psycho) should be(1)
    }

    "return a damage Multiplikator with blatt and water" in {
      getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Wasser) should be(1.1)
    }
    "return a damage Multiplikator with blatt and feuer" in {
      getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Feuer) should be(1.3)
    }
    "return a damage Multiplikator with blatt and blatt" in {
      getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Blatt) should be(1)
    }
    "return a damage Multiplikator with blatt and Psycho" in {
      getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Psycho) should be(1.2)
    }

    "return a damage Multiplikator with psycho and water" in {
      getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Wasser) should be(1)
    }
    "return a damage Multiplikator with psycho and feuer" in {
      getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Feuer) should be(1)
    }
    "return a damage Multiplikator with psycho and blatt" in {
      getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Blatt) should be(1)
    }
    "return a damage Multiplikator with psycho and Psycho" in {
      getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Psycho) should be(0.7)
    }
  }

}
