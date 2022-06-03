package de.htwg.se.pokelite
package model.CommandSpec

import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.FightingState
import model.commands.AttackCommand
import scala.util.Failure

import de.htwg.se.pokelite.model.NoAttackSelected
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class AttackCommandSpec extends AnyWordSpec{
  "AttackCommand" when {
    val newGame = Game()
    val state = FightingState()
    val game = newGame.setStateTo(state)
    "failure" in {
      AttackCommand("", state).doStep(game) should be (Failure(NoAttackSelected))
    }

  }

}
