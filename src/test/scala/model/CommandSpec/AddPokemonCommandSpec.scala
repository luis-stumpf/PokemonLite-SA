package de.htwg.se.pokelite
package model.CommandSpec

import model.{NoAttackSelected, NoInput, NoPokemonSelected}
import model.commands.{AddPlayerCommand, AddPokemonCommand, AttackCommand}
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.states.{DesicionState, FightingState, InitPlayerPokemonState, InitPlayerState}

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success}

class AddPokemonCommandSpec extends AnyWordSpec {
  "AddPokemonCommand" when {
    val newGame = Game(player1 = Some(PokePlayer(name = "Luis")), player2 = Some(PokePlayer(name = "Timmy")))
    val state = InitPlayerPokemonState()
    val game = newGame.setStateTo(state)
    "failure" in {
      AddPokemonCommand("", state).doStep(game) should be (Failure(NoPokemonSelected))
    }
    val command = AddPokemonCommand("123", state)
    "success" in {
      val res = command.doStep(game)
      val res1 = command.undoStep(res.get)
      res1 should be(game)
      val res2 = command.doStep(res.get)
      val res3 = command.doStep(res2.get)
      val res4 = command.undoStep(res3.get)
      res4 should be(res.get.setStateTo(DesicionState()))
    }

  }
}
