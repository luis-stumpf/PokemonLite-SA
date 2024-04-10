package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.pokelite.model.impl.game.Game

class ErrorSpec extends AnyWordSpec {
  "An Error" when {
    "No Input could be detected" should {
      "throw a No Input Message" in {
        val error = NoInput
        error.toString should be( "Error: Please Enter a valid input." )
      }
    }

    "a Name is too long" should {
      "throw a message with the wrong input" in {
        val error = NameTooLong( "zuLangerName" )
        error.toString should be(
          "Error: The Name \"zuLangerName\" is too long."
        )
        error.equals( NoInput ) should be( false )
        error.equals( NameTooLong( "zuLangerName" ) ) should be( true )
      }
    }

    "Wrong input is provided" should {
      "return the wrong input" in {
        val error = WrongInput( "hello" )
        error.input should be( "hello" )
      }
    }

    "No Pokemon is selected" should {
      "return the correct error message" in {
        val error = NoPokemonSelected
        error.toString should be("Error: You haven't selected a valid Pokemon.")
      }
    }

    "Not enough Pokemon are selected" should {
      "return the correct error message" in {
        val error = NotEnoughPokemonSelected(2)
        error.toString should be(
          s"Error: You have selected \"2\" valid Pokemon, but ${Game.maxPokePackSize.toString} is required."
        )
        error.amount should be(2)
      }
    }

    "Save cannot be undone" should {
      "throw a specific error message" in {
        val error = CanNotUndoSave
        error.toString should be("You cant undo a save command.")
      }
    }

    "Not able to save" should {
      "throw a specific error message" in {
        val error = NotAbleToSave
        error.toString should be("Error: Currently not able to save the Game, progress further!")
      }
    }

    "No save game found" should {
      "throw a specific error message" in {
        val error = NoSaveGameFound
        error.toString should be("Error: Wasnt able to find save game file!")
      }
    }

    "NoPlayerName" should {
      "return correct error message" in {
        val error = NoPlayerName
        error.toString should be("Error: There is No Player Name.")
      }
    }

    "NoPlayerExists" should {
      "return correct error message" in {
        val error = NoPlayerExists
        error.toString should be("Error: There Player.")
      }
    }

    "HorriblePokemonSelectionError" should {
      "return correct error message" in {
        val error = HorriblePokemonSelectionError
        error.toString should be("For some reason Player 2 has Pokemon but Player 1 doesnt. Everything is F****d!")
      }
    }

    "NoPlayerToRemove" should {
      "return correct error message" in {
        val error = NoPlayerToRemove
        error.toString should be("Error: No Player to remove.")
      }
    }

    "NoDesicionMade" should {
      "return correct error message" in {
        val error = NoDesicionMade
        error.toString should be("Error: Couldnt find a valid Desicion.")
      }
    }

    "HorriblePlayerNameError" should {
      "return correct error message" in {
        val error = HorriblePlayerNameError
        error.toString shouldBe "For some reason Player 2 has a name but Player 1 doesnt. Everything is F****d!"
      }
    }

    "NothingToRedo" should {
      "return correct error message" in {
        val error = NothingToRedo
        error.toString shouldBe "Error: There is nothing to redo."
      }
    }

    "CanNotUndoLoad" should {
      "return correct error message" in {
        val error = CanNotUndoLoad
        error.toString shouldBe "You cant undo a load command."
      }
    }

    "NothingToUndo" should {
      "return correct error message" in {
        val error = NothingToUndo
        error.toString shouldBe "Error: There is nothing to undo."
      }
    }

    "DeadPokemon" should {
      "return correct error message" in {
        val error = DeadPokemon
        error.toString shouldBe "Error: Your current Pokemon is dead and not able to attack!"
      }
    }
  }
}
