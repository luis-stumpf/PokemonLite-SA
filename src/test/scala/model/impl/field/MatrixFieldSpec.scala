package model.impl.field

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.impl.pokePlayer.PokePlayer
import model.PokePack
import model.Pokemon
import model.PokemonType
import model.impl.field.MatrixField

class MatrixFieldSpec extends AnyWordSpec {
  "A MatrixField" when {
    "new" should {
      val matrixField =
        MatrixField(
          150,
          30,
          PokePlayer(
            "Luis",
            PokePack( List( Some( Pokemon.apply( PokemonType.Glurak ) ) ) )
          ),
          PokePlayer(
            "Timmy",
            PokePack( List( Some( Pokemon.apply( PokemonType.Simsala ) ) ) )
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
