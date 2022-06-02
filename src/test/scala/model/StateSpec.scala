package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.commands.{AddPlayerCommand, ChangeStateCommand}
import de.htwg.se.pokelite.model.states.{InitPlayerState, InitState}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class StateSpec extends AnyWordSpec {
  "A State" when {
    "ActionState" should {
      val state = InitState()
      "set state" in {
        state.initPlayers() should be(Some(ChangeStateCommand( InitState(), InitPlayerState() )))
      }
    }
  }

}
