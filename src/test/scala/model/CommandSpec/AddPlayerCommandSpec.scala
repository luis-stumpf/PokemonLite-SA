package de.htwg.se.pokelite
package model.CommandSpec

import model.NoInput
import model.commands.AddPlayerCommand
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.states.{ InitPlayerPokemonState, InitPlayerState, InitState }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{ Failure, Success, Try }

class AddPlayerCommandSpec extends AnyWordSpec {
  "AddPlayerCommand" when {
    val newGame = Game()
    val state = InitPlayerState()
    val game = newGame.setStateTo( state )
    "failure" in {
      AddPlayerCommand( "", state ).doStep( game ) should be( Failure( NoInput ) )
    }
    val command = AddPlayerCommand( "luis", state )
    "success" in {
      val res = command.doStep( game )
      val res1 = command.undoStep( res.get )
      res1 should be( game )
      val res2 = command.doStep( res.get )
      val res3 = command.undoStep( res2.get )
      res3 should be( res.get )
    }

  }

}
