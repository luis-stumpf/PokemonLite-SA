package controller.impl

import model.PokemonType.Glurak
import controller.commands.ChangeStateCommand
import model.impl.game.Game
import model.impl.pokePlayer.*
import model.State.*
import util.{ Observer, UndoManager }
import model.GameInterface
import fileIo.FileIOInterface

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.util.Success
import scala.util.Failure
import di.PersistenceModule

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {

    val controller = Controller( using PersistenceModule.given_FileIOInterface )
    "have a Undo Manager" in {
      assert( controller.undoManager.isInstanceOf[UndoManager[GameInterface]] )
    }

    "have a Game of type Game" in {
      assert( controller.game.isInstanceOf[GameInterface] )
    }

    "notify its observers on change" in {
      class TestObserver( controller: Controller ) extends Observer:
        controller.add( this )
        var bing = false

        def update( message: String ) = bing = true
      val testObserver = TestObserver( controller )
      testObserver.bing should be( false )
      controller.doAndPublish( controller.initPlayers )
      testObserver.bing should be( true )
    }

    "be able to do and publish" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
    }

    "be able to handle a failure" in {
      controller.game = Game()
      val initialGame = Game()
      controller.save() match {
        case Success( savedGame ) =>
          savedGame should equal( initialGame )
        case Failure( exception ) =>
          fail( s"Saving the game failed: ${exception.getMessage}" )
      }
      controller.doAndPublish( controller.undoMove )
      controller.game should equal( initialGame )

    }

    "be able to undo a move" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.undoMove()
      controller.game.state should be( InitPlayerState )
    }

    "be able to redo a move" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.undoMove()
      controller.game.state should be( InitPlayerState )
      controller.redoMove()
      controller.game.state should be( InitPlayerState )
    }

    "be able to init players" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
    }

    "be able to add a player" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.game.state should be( InitPlayerState )
    }

    "be able to add a player and change state" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.game.state should be( InitPlayerPokemonState )
    }

    "be able to add pokemons" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.addPokemons, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
    }

    "be able to save the game" in {
      controller.game = Game()
      val initialGame = Game()
      controller.save() match {
        case Success( savedGame ) =>
          savedGame should equal( initialGame )
        case Failure( exception ) =>
          fail( s"Saving the game failed: ${exception.getMessage}" )
      }
    }

    "be able to load the game" in {
      controller.game = Game()
      val initialGame = Game()
      controller.load() match {
        case Success( loadedGame ) =>
          loadedGame should equal( initialGame )
        case Failure( exception ) =>
          fail( s"Loading the game failed: ${exception.getMessage}" )
      }
    }

    "be able to switch pokemon" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.doAndPublish( controller.addPlayer, "Test1" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.addPokemons, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.selectPokemon, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
    }

    "be able to attack" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.doAndPublish( controller.addPlayer, "Test1" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.addPokemons, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.selectPokemon, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.nextMove, "attack" )
      controller.game.state should be( InitPlayerPokemonState )
    }

    "be able to restart the game" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.doAndPublish( controller.addPlayer, "Test1" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.addPokemons, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.selectPokemon, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.restartTheGame )
      controller.game.state should be( InitState )
    }

    "be able to get the game" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.doAndPublish( controller.addPlayer, "Test1" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.addPokemons, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.selectPokemon, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.getGame() match {
        case Success( game ) =>
          game should equal( controller.game )
        case Failure( exception ) =>
          fail( s"Getting the game failed: ${exception.getMessage}" )
      }
    }

    "be able to attack with a pokemon" in {
      controller.doAndPublish( controller.initPlayers )
      controller.game.state should be( InitPlayerState )
      controller.doAndPublish( controller.addPlayer, "Test" )
      controller.doAndPublish( controller.addPlayer, "Test1" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.addPokemons, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.selectPokemon, "Glurak" )
      controller.game.state should be( InitPlayerPokemonState )
      controller.doAndPublish( controller.attackWith, "attack" )
      controller.game.state should be( InitPlayerPokemonState )
    }

  }
}
