package de.htwg.se.pokelite
package model.CommandSpec

import model.commands.AttackCommand
import model.impl.game.Game
import model.states.FightingState
import model.{ NoInput, NoValidAttackSelected }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Failure

class AttackCommandSpec extends AnyWordSpec {
  "AttackCommand" when {
    val newGame = Game()
    val state = FightingState()
    val game = newGame.setStateTo( state )
    "failure" in {
      AttackCommand( "", state ).doStep( game ) should be( Failure( NoInput ) )
    }

  }

}
