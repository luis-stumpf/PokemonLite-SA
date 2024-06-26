package fileIo.xml

import model.PokemonType.{ Glurak, Simsala }
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.State.*
import model.{ PokePack, Pokemon }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class FileIOSpec extends AnyWordSpec {

  val fileIO = FileIOXml()
  val game = Game()

  "The FileIO with the XML implement" should {
    "save the current game" in {
      fileIO.save( game )
    }

    "load a game an its stats from game.xml" in {

      fileIO.load should be( game )

    }

    "save from json" in {

      fileIO.save( game.toJson )
      fileIO.load should be( game )

    }

  }
}
