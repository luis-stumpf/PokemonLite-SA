package de.htwg.se.pokelite
package aview

import model.{ AttackMove, PlayerMove, PokeMove, PokePlayer, Pokemon, Field }
import model.PokemonType.Glurak

import de.htwg.se.pokelite.controller.Controller
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec {
  "Tui" should {
    val tui = TUI(Controller(Field(50, PokePlayer( "", 1), PokePlayer( "", 2 ))))
    "recognize attack input 0" in {
      tui.chooseAttack("1") should be(Some(AttackMove(0)))
    }
    "rec attach input 1" in {
      tui.chooseAttack("2") should be(Some(AttackMove(1)))
    }
    "choose pokemon 1" in{
      tui.inputAnalysisPokemon("1") should be(Some(PokeMove(List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) ))))
    }
  }
}