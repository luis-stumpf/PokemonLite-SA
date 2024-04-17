package fileIo.xml

import model.PokemonType.{ Glurak, Simsala }
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.State.*
import model.{ PokePack, Pokemon }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class FileIOSpec extends AnyWordSpec {

  val fileIO = FileIO()
  val game = Game(
    FightingState,
    Some(
      PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) )
    ),
    Some(
      PokePlayer(
        "Timmy",
        PokePack( List( Some( Pokemon.apply( Simsala ) ) ) )
      )
    )
  )
  "The FileIO with the XML implement" should {
    "save the current game" in {
      fileIO.save( game )
    }

    "load a game an its stats from game.xml" in {

      fileIO.load should be( game )

    }
  }
}
