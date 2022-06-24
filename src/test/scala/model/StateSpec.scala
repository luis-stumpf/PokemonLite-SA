package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.commands.{ AddPlayerCommand, ChangeStateCommand }
import de.htwg.se.pokelite.model.states.{ DesicionState, FightingState, GameOverState, InitPlayerPokemonState, InitPlayerState, InitState, SwitchPokemonState }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import model.impl.game.Game
import model.GameInterface

class StateSpec extends AnyWordSpec {

  "A State Object" when {
    "called" should {
      "create an xml with intitstate" in {
        val state = InitState()
        State.fromXML(
          <state>{state.toString}</state>
        ) should be (InitState())
      }
    }
  }

  "A State" when {
    "ActionState" should {
      val state = InitState()
      "set state" in {
        state.initPlayers() shouldBe a [Some[InitPlayerState]]

      }
    }
    "InitPlayerState" should {
      val state = InitPlayerState()
      state.addPlayer("luis") shouldBe a [Some[InitPlayerState]]
    }
    "InitPlayerPokemonState" should{
      val state = InitPlayerPokemonState()
      state.addPokemons("") shouldBe a [Some[InitPlayerPokemonState]]
    }
    "DesicionState" should {
      val state = DesicionState()
      state.nextMove("") shouldBe a [Some[DesicionState]]
    }
    "FightingState" should{
      val state = FightingState()
      state.attackWith("") shouldBe a [Some[DesicionState]]
    }
    "SwitchPokemonState" should{
      val state = SwitchPokemonState()
      state.switchPokemonTo("") shouldBe a [Some[DesicionState]]
    }
    "GameOverState" should{
      val state = GameOverState()
      state.restartTheGame(Game()) shouldBe a [Some[InitState]]
    }



    "lol" should{
      val state = GameOverState()
      state.addPokemons("") should be(None)
      state.initPlayers() should be(None)
      state.addPlayer("luis") should be(None)
      state.attackWith("") should be(None)
      state.switchPokemon("") should be(None)
      state.nextMove("") should be(None)
      state.switchPokemon("") should be(None)
      state.switchPokemonTo("") should be(None)
      val state2 = InitState()
      state2.restartTheGame(Game()) should be(None)
    }

  }

}
