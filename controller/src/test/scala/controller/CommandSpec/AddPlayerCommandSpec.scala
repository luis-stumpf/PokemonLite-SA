package model.CommandSpec

import util.NoInput
import controller.commands.AddPlayerCommand
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.State.*

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success, Try }

class AddPlayerCommandSpec extends AnyWordSpec {
  "AddPlayerCommand" when {
    val newGame = Game()
    val state = InitPlayerState
    val game = newGame.setStateTo( state )
    "failure" in {
      AddPlayerCommand( "", state ).doStep( game ) should be(
        Failure( NoInput )
      )
    }
    val command = AddPlayerCommand( "luis", state )
    "success" in {
      val res = command.doStep( game )
      res should be( game.addPlayerWith( "luis" ) )
      val res1 = command.undoStep( res.get )
      res1 should be( Success( game ) )
    }

  }

}
