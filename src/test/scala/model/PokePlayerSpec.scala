package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.PokemonType.{ Brutalanda, Glurak, Simsala }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PokePlayerSpec extends AnyWordSpec {
  "PokePlayerSpec" should {
    val pokeList1 = List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) )
    val pokeList2 = List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala ) ) )
    val pokeList3 = List( Some( Pokemon( Simsala ) ), Some( Pokemon( Simsala ) ) )
    val p1 = PokePlayer("Luis", 1)
    val p2 = PokePlayer("Timmy", 1, pokeList2, 1)
    val p3 = PokePlayer("Otto", 0, pokeList3, 2)
    "have a name in form 'name'" in {
      p1.toString should be("Luis")
      p2.toString should be("Timmy")
      p3.toString should be("Otto")
    }
    "check Pokemons" in {
      p1.pokemons should be(List(None))
      p2.pokemons should be(Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala ) ))
      p1.currentPoke should be(0)
      p3.currentPoke should be(2)
      p1.number should be(1)
      p3.number should be(0)
    }
    "set Player Name in form new PokePlayer" in {
      p1.setPokePlayerNameTo("Udo").name should be("Udo")
    }
    "set Pokemons to" in {
      p1.setPokemonTo(pokeList1) should be(pokeList1)
      p2.setPokemonTo(pokeList3) should be(pokeList3)
    }
  }
}
