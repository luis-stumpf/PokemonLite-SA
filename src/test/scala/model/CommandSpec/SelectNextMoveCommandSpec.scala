package de.htwg.se.pokelite
package model.CommandSpec

import model.impl.game.Game

import de.htwg.se.pokelite.model.{NoDesicionMade, NoInput}
import de.htwg.se.pokelite.model.commands.SelectNextMoveCommand
import de.htwg.se.pokelite.model.states.{DesicionState, InitPlayerPokemonState}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Failure

class SelectNextMoveCommandSpec extends AnyWordSpec {
  "SelectNextMoveCommand" when {
    val newGame = Game()
    val state = DesicionState()
    val game = newGame.setStateTo(state)
    "failure" in {
      SelectNextMoveCommand("", state).doStep(game) should be(Failure(NoInput))
    }

  }
}
