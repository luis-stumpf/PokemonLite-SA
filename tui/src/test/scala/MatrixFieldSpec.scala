package tui

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.impl.pokePlayer.PokePlayer
import model.PokePack
import model.Pokemon
import model.PokemonType
import tui.MatrixField
import model.impl.game.Game

class MatrixFieldSpec extends AnyWordSpec {
  "A MatrixField" when {
    "new" should {
      val matrixField =
        MatrixField(
          150,
          30,
          Game(
            player1 = Some(
              PokePlayer(
                "Luis",
                PokePack( List( Some( Pokemon.apply( PokemonType.Glurak ) ) ) )
              )
            ),
            player2 = Some(
              PokePlayer(
                "Timmy",
                PokePack( List( Some( Pokemon.apply( PokemonType.Simsala ) ) ) )
              )
            )
          )
        )
      "print the string" in {
        matrixField.toString() should be(
          "+--------------------------------------------------------------------------+-------------------------------------------------------------------------+\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                   Luis                   |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                   Glurak HP: 150         |      1. Glut                                      2. Flammenwurf        |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "|      Simsala HP: 130                                                     |      3. Biss                                      4. Inferno            |\n" +
            "|                                                                          |                                                                         |\n" +
            "|      Timmy                                                               |                                                                         |\n" +
            "|                                                                          |                                                                         |\n" +
            "+--------------------------------------------------------------------------+-------------------------------------------------------------------------+\n"
        )
      }
    }
  }
}
