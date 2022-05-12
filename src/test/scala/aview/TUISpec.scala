package de.htwg.se.pokelite
package aview

import model.{ AttackMove, PlayerMove, PokeMove, PokePlayer, Pokemon, Field }
import model.PokemonType.Glurak

import de.htwg.se.pokelite.controller.Controller
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec {
  "Tui" should {
    val tui = TUI(Controller(Field(50, PokePlayer( "", 1), PokePlayer( "", 2 ))))
  }
}