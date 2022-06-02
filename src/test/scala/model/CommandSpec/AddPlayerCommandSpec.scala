package de.htwg.se.pokelite
package model.CommandSpec
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.{InitPlayerPokemonState, InitPlayerState}
import model.commands.AddPlayerCommand

import de.htwg.se.pokelite.model.NoInput
import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.util.Try

import scala.util.Success

class AddPlayerCommandSpec extends AnyWordSpec {
  "AddPlayerCommand" when {
    val newGame:Game = Game()
    val newGameWithPlayer = Game(player2 = Some(PokePlayer("name")))
    "doStep" should {
      val state = InitPlayerState()
      val state1 = InitPlayerPokemonState()
      "success" in {
        val command = AddPlayerCommand("Luis", state)
        val res = command.doStep(newGame)
        res.get.toString should be("" +
          "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                         Luis     |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n")
        val res1 = command.doStep(res.get)
        res1.get.state should be(InitPlayerPokemonState())

      }
      "failure" in {
        val command = AddPlayerCommand("", state = state)
        val res = command.doStep(newGame)
        res.isFailure should be(true)
      }
    }
    "undo Step" should{
      val state = InitPlayerState()
      "success" in {
        val command = AddPlayerCommand("Luis", state)
        val res = command.doStep(newGame)
        val res1 = command.undoStep(res.get)
        res1.toString should be("" +
          "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n")
        val res2 = command.doStep(res.get)
        val res3 = command.undoStep(res2.get)
        res3.toString should be("" +
          "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                         Luis     |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n")



      }

    }
  }

}
