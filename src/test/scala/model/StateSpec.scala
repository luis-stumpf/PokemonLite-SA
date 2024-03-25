package de.htwg.se.pokelite
package model

import model.GameInterface
import model.commands.{ AddPlayerCommand, ChangeStateCommand }
import model.impl.game.Game
import model.states.*

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class StateSpec extends AnyWordSpec {

  "A State" when {
    "ActionState" should {
      "set state" in {
        val state = InitState()
        state.initPlayers() shouldBe a[Some[InitPlayerState]]

      }
    }
    "InitPlayerState" should {
      "add player" in {
        val state = InitPlayerState()
        state.addPlayer( "luis" ) shouldBe a[Some[InitPlayerState]]
      }
    }
    "InitPlayerPokemonState" should {
      "add pokemons" in {
        val state = InitPlayerPokemonState()
        state.addPokemons( "" ) shouldBe a[Some[InitPlayerPokemonState]]
      }
    }
    "DesicionState" should {
      "next move" in {
        val state = DesicionState()
        state.nextMove( "" ) shouldBe a[Some[DesicionState]]
      }
    }
    "FightingState" should {
      "attack with" in {
        val state = FightingState()
        state.attackWith( "" ) shouldBe a[Some[DesicionState]]
      }
    }
    "SwitchPokemonState" should {
      "switch pokemon" in {
        val state = SwitchPokemonState()
        state.switchPokemonTo( "" ) shouldBe a[Some[DesicionState]]
      }
    }
    "GameOverState" should {
      "restart the game" in {
        val state = GameOverState()
        state.restartTheGame( Game() ) shouldBe a[Some[InitState]]
      }
    }

    "lol" should {
      val state = GameOverState()
      state.addPokemons( "" ) should be( None )
      state.initPlayers() should be( None )
      state.addPlayer( "luis" ) should be( None )
      state.attackWith( "" ) should be( None )
      state.switchPokemon( "" ) should be( None )
      state.nextMove( "" ) should be( None )
      state.switchPokemon( "" ) should be( None )
      state.switchPokemonTo( "" ) should be( None )
      val state2 = InitState()
    }

  }

}
