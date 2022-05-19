package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.PokemonType.Glurak
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MoveSpec extends AnyWordSpec {
  "A Move" should {
    "A PlayerMove" should{
      val move = PlayerMove("name")
      move.name should be("name")
    }

    "A PokeMove" should{
      val move = PokeMove(List(Some(Pokemon(Glurak))))
      move.pokemons should be(List(Some(Pokemon(Glurak))))
    }

    "A AttackMove" should{
      val move = AttackMove(3)
      move.attack should be(3)
    }
    "A ChangePokeMove" should{
      val move = ChangePokeMove(1)
      move.i should be(1)
    }
  }
}
