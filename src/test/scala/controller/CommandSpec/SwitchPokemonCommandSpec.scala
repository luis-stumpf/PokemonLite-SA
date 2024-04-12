package model.CommandSpec

import model.PokemonType.{ Glurak, Simsala }
import controller.commands.SwitchPokemonCommand
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.State.*
import model.{ PokePack, Pokemon }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success }
import util.NoInput

class SwitchPokemonCommandSpec extends AnyWordSpec {
  "SwitchPokemonCommand" when {
    val newGame = Game(
      player1 = Some(
        PokePlayer(
          "Luis",
          PokePack( List( Some( Pokemon.apply( Glurak ) ) ) )
        )
      ),
      player2 = Some(
        PokePlayer(
          "Timmy",
          PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
        )
      )
    )
    val state = SwitchPokemonState
    val game = newGame.setStateTo( state )
    "failure" in {
      SwitchPokemonCommand( "", state ).doStep( game ) should be(
        Failure( NoInput )
      )
    }
    val command = SwitchPokemonCommand( "1", state )
    "success" in {
      val res = command.doStep( game )
      res should be( game.selectPokemonFrom( "1" ) )
      val res1 = command.undoStep( res.get )
      res1 should be( Success( game ) )
    }

  }
}
