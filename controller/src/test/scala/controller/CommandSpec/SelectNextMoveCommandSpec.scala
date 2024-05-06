package controller.CommandSpec

import controller.commands.SelectNextMoveCommand
import model.impl.game.Game
import model.State.*
import util.{ NoDesicionMade, NoInput }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Failure
import model.impl.pokePlayer.PokePlayer
import model.PokePack
import model.Pokemon
import model.PokemonType
import org.scalatest.BeforeAndAfterEach
import util.WrongInput

class SelectNextMoveCommandSpec extends AnyWordSpec with BeforeAndAfterEach {
  var game: Game = _

  override def beforeEach(): Unit = {
    game = Game(
      DesicionState,
      Some(
        PokePlayer(
          "Luis",
          PokePack( List( Some( Pokemon.apply( PokemonType.Simsala ) ) ) )
        )
      ),
      Some(
        PokePlayer(
          "Timmy",
          PokePack( List( Some( Pokemon.apply( PokemonType.Glurak ) ) ) )
        )
      )
    )
    super.beforeEach()
  }

  "SelectNextMoveCommand" when {

    "failure" in {
      SelectNextMoveCommand( "", game.state ).doStep( game ) should be(
        Failure( NoInput )
      )
    }

    "failure 3" in {
      SelectNextMoveCommand( "3", game.state ).doStep( game ) should be(
        Failure( WrongInput( "3" ) )
      )
    }

  }
}
