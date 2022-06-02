package de.htwg.se.pokelite
package model.CommandSpec
import de.htwg.se.pokelite.model.impl.game.Game
import de.htwg.se.pokelite.model.states.InitPlayerState
import model.commands.AddPlayerCommand
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

class AddPlayerCommandSpec extends AnyWordSpec {
  "AddPlayerCommand" when {
    val newGame:Game = Game()
    "doStep" should {
      val state = InitPlayerState()
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

      }

    }
  }

}
