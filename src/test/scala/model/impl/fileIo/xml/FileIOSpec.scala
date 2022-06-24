package de.htwg.se.pokelite
package model.impl.fileIo.xml

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.impl.game.Game

import de.htwg.se.pokelite.model.{PokePack, Pokemon}
import de.htwg.se.pokelite.model.PokemonType.{Glurak, Simsala}
import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer
import de.htwg.se.pokelite.model.states.FightingState

class FileIOSpec extends AnyWordSpec {

  val fileIO = FileIO()
  val game = Game(FightingState(),
    Some(PokePlayer("Luis", PokePack(List(Some(Pokemon.apply(Glurak)))))),
    Some(PokePlayer("Timmy", PokePack(List(Some(Pokemon.apply(Simsala)))))))
  "The FileIO with the XML implement" should {
    "save the current game" in {
      fileIO.save(game)
    }

    "load a game an its stats from game.xml" in {

      fileIO.load should be(game)

    }
  }
}