package de.htwg.se.pokelite
package model.CommandSpec
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.{InitPlayerPokemonState, InitPlayerState, InitState}
import model.commands.AddPlayerCommand

import de.htwg.se.pokelite.model.NoInput
import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success, Try}

class AddPlayerCommandSpec extends AnyWordSpec {
  "AddPlayerCommand" when {
    "A player command " should {
      val playerCommand = AddPlayerCommand("Luis", InitPlayerState())
      playerCommand.name should be("Luis")
      playerCommand.state should be(InitPlayerState())
    }
    val newGame:Game = Game()
    val newGameWithPlayer = Game(player2 = Some(PokePlayer("name")))
    "doStep" should {
      val state = InitPlayerState()
      val state1 = InitPlayerPokemonState()
      "success" in {
        val command = AddPlayerCommand("Luis", state)
        val res = command.doStep(newGame)
        val game = newGame.addPlayer("Luis")
        res should be (Success(game.setStateTo(InitPlayerState())))
        val res1 = command.doStep(res.get)
        val newNewGame = game.addPlayer("Luis")
        res1 should be (Success(newNewGame.setStateTo(InitPlayerPokemonState())))

      }
      "failure" in {
        val command = AddPlayerCommand("", state = state)
        val res = command.doStep(newGame)
        res shouldBe Failure(NoInput)
      }
    }
    "undo Step" should{
      val state = InitPlayerState()
      "success" in {
        val command = AddPlayerCommand("Luis", state)
        val res = command.doStep(newGame)
        val res1 = command.undoStep(res.get)
        res1 should be(Game(state = InitPlayerState()))
        val res2 = command.doStep(res.get)
        val res3 = command.undoStep(res2.get)
        res3 should be(Game(player1 = Some(PokePlayer("Luis")), state = InitPlayerPokemonState()))



      }

    }
  }

}
