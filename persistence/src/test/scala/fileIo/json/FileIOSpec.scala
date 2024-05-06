package fileIo.json

import model.PokemonType.{ Glurak, Simsala }
import model.impl.game.Game
import model.impl.pokePlayer.PokePlayer
import model.State.*
import model.{ PokePack, Pokemon }

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class FileIOSpec extends AnyWordSpec {
  val fileIO = FileIOJson()
  val game = Game()

  "The FileIO with the Json implement" should {
    "save the current game" in {
      fileIO.save( game )
      val res = fileIO.load
      res should be( game )

    }

    "load a game an its stats from game.json" in {
      val res = fileIO.load
      res should be( game )

    }

    "save from json" in {
      fileIO.save( game.toJson )
      val res = fileIO.load
      res should be( game )

    }

  }

}
