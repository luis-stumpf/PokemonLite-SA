package de.htwg.se.pokelite
package model.impl.game

import model.PokemonArt

import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer
import de.htwg.se.pokelite.model.states.{ InitPlayerState, InitState }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec {
  "The Game Class" when {

    "empty" should {
      var game = Game()
      "have the InitState" in {
        assert(game.state == InitState())
      }
      "have no players" in {
        assert(game.player1 == None && game.player2 == None)
      }
      "be able to set the state" in {
        game.setStateTo(InitPlayerState()) should be(Game(InitPlayerState()))
      }
      "be able to add a Player" in {
        game.addPlayer("Luis") should be(Game(InitState(), Some(PokePlayer("Luis"))))
      }
    }
  }
  "The Game Object" should {
    "return a damage Multiplikator with water and water" in {
      Game.getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Wasser) should be(1)
    }
    "return a damage Multiplikator with water and feuer" in {
      Game.getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Feuer) should be(1.2)
    }
    "return a damage Multiplikator with water and blatt" in {
      Game.getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Blatt) should be(0.5)
    }
    "return a damage Multiplikator with water and Psycho" in {
      Game.getDamageMultiplikator(PokemonArt.Wasser, PokemonArt.Psycho) should be(1)
    }

    "return a damage Multiplikator with fire and water" in {
      Game.getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Wasser) should be(0.5)
    }
    "return a damage Multiplikator with fire and feuer" in {
      Game.getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Feuer) should be(1)
    }
    "return a damage Multiplikator with fire and blatt" in {
      Game.getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Blatt) should be(1.3)
    }
    "return a damage Multiplikator with fire and Psycho" in {
      Game.getDamageMultiplikator(PokemonArt.Feuer, PokemonArt.Psycho) should be(1)
    }

    "return a damage Multiplikator with blatt and water" in {
      Game.getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Wasser) should be(1.1)
    }
    "return a damage Multiplikator with blatt and feuer" in {
      Game.getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Feuer) should be(1.3)
    }
    "return a damage Multiplikator with blatt and blatt" in {
      Game.getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Blatt) should be(1)
    }
    "return a damage Multiplikator with blatt and Psycho" in {
      Game.getDamageMultiplikator(PokemonArt.Blatt, PokemonArt.Psycho) should be(1.2)
    }

    "return a damage Multiplikator with psycho and water" in {
      Game.getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Wasser) should be(1)
    }
    "return a damage Multiplikator with psycho and feuer" in {
      Game.getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Feuer) should be(1)
    }
    "return a damage Multiplikator with psycho and blatt" in {
      Game.getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Blatt) should be(1)
    }
    "return a damage Multiplikator with psycho and Psycho" in {
      Game.getDamageMultiplikator(PokemonArt.Psycho, PokemonArt.Psycho) should be(0.7)
    }
    "have a poke Pack size set to a number" in {
      assert(Game.pokePackSize.isValidInt)
    }
  }

}
