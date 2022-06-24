package de.htwg.se.pokelite
package model.impl.pokePlayer

import de.htwg.se.pokelite.model.PokemonType.{Brutalanda, Glurak, Simsala}
import model.PokePack
import model.Pokemon
import model.impl.pokePlayer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PokePlayerSpec extends AnyWordSpec {
  "PokePlayerSpec" should {
    val pokeList1 = PokePack(List(Some(Pokemon(Glurak)), Some(Pokemon(Glurak))))
    val pokeList2 = PokePack(List(Some(Pokemon(Brutalanda)), Some(Pokemon(Simsala, 0, true))))
    val pokeList3 = PokePack(List(Some(Pokemon(Simsala)), Some(Pokemon(Simsala))))
    val pokeListDead = PokePack(List(Some(Pokemon(Simsala, 0, true)), Some(Pokemon(Glurak, 0, true))))
    val p1 = PokePlayer("Luis")
    val p2 = PokePlayer("Timmy", pokeList2, 1)
    val p3 = PokePlayer("Otto", pokeList3, 2)
    val p4 = PokePlayer("Paul", pokeListDead, 1)
    "have a name in form 'name'" in {
      p1.toString should be("Luis")
      p2.toString should be("Timmy")
      p3.toString should be("Otto")
    }
    "check Pokemons" in {
      p1.pokemons should be(PokePack.apply(List(None)))
      p2.pokemons should be(PokePack(List(Some(Pokemon(Brutalanda)), Some(Pokemon(Simsala, 0, true))), 2))
      p1.currentPoke should be(0)
      p3.currentPoke should be(2)
    }
    "set Player Name in form new PokePlayer" in {
      p1.setPokePlayerNameTo("Udo").name should be("Udo")
    }
    "set Pokemons to" in {
      p1.setPokemonTo(pokeList1) should be(p1.copy(pokemons = pokeList1))
      p2.setPokemonTo(pokeList3) should be(p2.copy(pokemons = pokeList3))
    }
    "check if at least one pokemon in the Pack is alive" in {
      p2.checkForDefeat() should be(false)
    }
    "check if all Pokemon in a pack are dead" in {
      p4.checkForDefeat() should be(true)
    }
  }
}