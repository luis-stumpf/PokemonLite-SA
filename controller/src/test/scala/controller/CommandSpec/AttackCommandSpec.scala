package controller.CommandSpec

import controller.commands.AttackCommand
import model.impl.game.Game
import model.State.FightingState
import util.{ NoInput, NoValidAttackSelected }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Failure
import scala.util.Success
import model.impl.pokePlayer.PokePlayer
import model.PokePack
import model.Pokemon
import model.PokemonType
import model.State
import org.scalatest.BeforeAndAfterEach

class AttackCommandSpec extends AnyWordSpec with BeforeAndAfterEach {

  var game: Game = _

  override def beforeEach(): Unit = {
    game = Game(
      FightingState,
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
    super.beforeEach() // To be stackable, must call super.beforeEach()
  }
  "AttackCommand" when {
    "success" in {
      AttackCommand( "1", game.state ).doStep( game ) should be(
        Success(
          game
            .interpretAttackSelectionFrom( "1" )
            .get
            .setStateTo( State.DesicionState )
        )
      )
    }

    "success game Over" in {
      val gameNew = Game(
        FightingState,
        Some(
          PokePlayer(
            "Luis",
            PokePack( List( Some( Pokemon.apply( PokemonType.Simsala ) ) ) )
          )
        ),
        Some(
          PokePlayer(
            "Timmy",
            PokePack(
              List(
                Some( Pokemon.apply( PokemonType.Glurak ).reduceHP( 140 ) )
              )
            )
          )
        )
      )
      AttackCommand( "1", gameNew.state ).doStep( gameNew ) should be(
        Success(
          gameNew
            .interpretAttackSelectionFrom( "1" )
            .get
            .setStateTo( State.GameOverState )
        )
      )
    }

  }

}
