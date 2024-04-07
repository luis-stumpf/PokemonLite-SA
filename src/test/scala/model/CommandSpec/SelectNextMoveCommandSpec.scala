package de.htwg.se.pokelite
package model.CommandSpec

import model.commands.SelectNextMoveCommand
import model.impl.game.Game
import model.State.*
import model.{ NoDesicionMade, NoInput }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Failure
import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer
import de.htwg.se.pokelite.model.PokePack
import de.htwg.se.pokelite.model.Pokemon
import de.htwg.se.pokelite.model.PokemonType

class SelectNextMoveCommandSpec extends AnyWordSpec {
  "SelectNextMoveCommand" when {
    val newGame =
      Game(
        DesicionState,
        Some(
          PokePlayer(
            "Luis",
            PokePack(
              List(
                Some(
                  Some( Pokemon.apply( PokemonType.Glurak ) ).get
                    .reduceHP( 10.0 )
                )
              )
            )
          )
        ),
        Some(
          PokePlayer(
            "Timmy",
            PokePack( List( Some( Pokemon.apply( PokemonType.Simsala ) ) ) )
          )
        )
      )

    val game = newGame.setStateTo( DesicionState )

    "failure" in {
      SelectNextMoveCommand( "", game.state ).doStep( game ) should be(
        Failure( NoInput )
      )
    }

    // fix

    /*
    val command = SelectNextMoveCommand( "1", newGame.state )
    "success" in {
      val res = command.doStep( newGame )
      res should be( newGame.interpretAttackSelectionFrom( "1" ) )
    }
     */

  }
}
