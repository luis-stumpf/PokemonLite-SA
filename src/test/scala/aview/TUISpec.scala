package de.htwg.se.pokelite
package aview

import model.{ AttackMove, Field, PlayerMove, PokeMove, PokePlayer, Pokemon }
import model.PokemonType.{ Brutalanda, Glurak, Simsala }

import de.htwg.se.pokelite.controller.Controller
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec {
  "Tui" should {
    val tui = TUI(Controller(Field(50, PokePlayer( "", 1), PokePlayer( "", 2 ))))
    "recognize attack input 1" in {
      tui.chooseAttack("1") should be(Some(AttackMove(0)))
    }
    "rec attach input 2" in {
      tui.chooseAttack("2") should be(Some(AttackMove(1)))
    }
    "recognize attack input 3" in {
      tui.chooseAttack("3") should be(Some(AttackMove(2)))
    }
    "rec attach input 4" in {
      tui.chooseAttack("4") should be(Some(AttackMove(3)))
    }
    "choose pokemon 1" in{
      tui.inputAnalysisPokemon("1") should be(Some(PokeMove(List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) ))))
    }
    "choose pokemon 2" in{
      tui.inputAnalysisPokemon("2") should be(Some(PokeMove(List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala ) ) ))))
    }
    "choose pokemon 3" in{
      tui.inputAnalysisPokemon("3") should be(Some(PokeMove(List( Some( Pokemon( Simsala ) ), Some( Pokemon( Simsala ) ) ))))
    }
  }
}