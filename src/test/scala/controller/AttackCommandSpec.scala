package de.htwg.se.pokelite
package controller

import de.htwg.se.pokelite.model.PokemonType.{ Brutalanda, Glurak, Simsala, Turtok }
import de.htwg.se.pokelite.model._
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class AttackCommandSpec extends AnyWordSpec{
  "AttackCommand" should {
    val attackCommand = AttackCommand(AttackMove(0))
    val field = Field( 50,
      PokePlayer( "", 1, PokePack( List( Some( Pokemon( Glurak ) ), Some( Pokemon( Simsala ) ) ) ) ),
      PokePlayer( "", 2, PokePack( List( Some( Pokemon( Turtok ) ), Some( Pokemon( Brutalanda ) ) ) ) ) )
    "no Step" in {
      val newField = attackCommand.noStep(field)
      field should be(newField)
    }
    "undoStep" in {
      val newField = attackCommand.doStep(field)
      newField.toString should be(
        "+--------------------------------------------------+--------------------------------------------------+\n" +
        "|                                                  |                                                  |\n" +
        "|                               Glurak HP: 150     |     1. Aquaknarre       2. Biss                  |\n" +
        "|                                                  |                                                  |\n" +
        "|                                                  |                                                  |\n" +
        "|                                                  |                                                  |\n" +
        "|     Turtok HP: 120                               |     3. Hydropumpe       4. Matschbombe           |\n" +
        "|                                                  |                                                  |\n" +
        "+--------------------------------------------------+--------------------------------------------------+\n"
      )
      val nextfield = newField.setNextTurn()
      val undoField = attackCommand.undoStep(nextfield)
      undoField.toString should be(
        "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                                  |                                                  |\n" +
          "|                               Glurak HP: 174     |     1. Aquaknarre       2. Biss                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|     Turtok HP: 120                               |     3. Hydropumpe       4. Matschbombe           |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n"
      )
      val redoField = attackCommand.redoStep(undoField)
      val redoNextField = redoField.setNextTurn()
      redoNextField should be(newField)
    }
  }

}
