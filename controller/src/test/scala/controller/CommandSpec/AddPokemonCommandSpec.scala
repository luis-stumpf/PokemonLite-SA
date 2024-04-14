package controller.CommandSpec

import controller.commands.{ AddPlayerCommand, AddPokemonCommand }
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.State.*
import util.{ NoInput, NoPokemonSelected }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success }

class AddPokemonCommandSpec extends AnyWordSpec {
  "AddPokemonCommand" when {
    val newGame = Game(
      player1 = Some( PokePlayer( name = "Luis" ) ),
      player2 = Some( PokePlayer( name = "Timmy" ) )
    )
    val state = InitPlayerPokemonState
    val game = newGame.setStateTo( state )
    "failure" in {
      AddPokemonCommand( "", state ).doStep( game ) should be(
        Failure( NoPokemonSelected )
      )
    }
    val command = AddPokemonCommand( "123", state )
    "success" in {
      val undo = command.undoStep( game )
      undo should be( Success( game.setStateTo( InitPlayerPokemonState ) ) )
      val res = command.doStep( game )
      res should be( game.interpretPokemonSelectionFrom( "123" ) )
      val res1 = command.undoStep( res.get )
      res1 should be( Success( game ) )
    }

  }
}
