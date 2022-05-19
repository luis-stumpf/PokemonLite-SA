package de.htwg.se.pokelite.model

import de.htwg.se.pokelite.model.PokemonType.{ Glurak, Simsala, Turtok }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class FieldSpec extends AnyWordSpec {
  "A PokemonLite Field" when {
    "empty" should {
      val field = new Field(50, PokePlayer("",1), PokePlayer("",2))
      "have a bar as String of form '+---+---+'" in {
        field.row() should be("+--------------------------------------------------+--------------------------------------------------+\n")
      }
      "have a cell as String of form '|  |  |'" in {
        field.col( 1) should be("|                                                  |                                                  |\n")
      }
      "have a scalable height" in {
        field.col(1) should be("|                                                  |                                                  |\n")
        field.col(2) should be("|                                                  |                                                  |\n"*2)
        field.col(3) should be("|                                                  |                                                  |\n"*3)
        field.col(4) should be("|                                                  |                                                  |\n"*4)

      }
      "have a mesh in the form " +
        "+-+-+" +
        "| | |" +
        "+-+-+" in {
        field.mesh(1) should be(
          "+--------------------------------------------------+--------------------------------------------------+\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "|                                                  |                                                  |\n" +
          "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
    "with input" should {
      val field = Field(50, PokePlayer("Luis", 1), PokePlayer("Luis", 2))

      "calc space Int" in {
        field.calcSpace(0.9) should be(45)
        field.calcSpace(0.9, "Luis") should be(41)
      }
      "Field state '1 or 2'" in{
        field.isControlledBy should be(1)
        field.setNextTurn().isControlledBy should be(2)
      }
      "have a mesh in form of \n" +
      "+----------------+----------------+\n" +
      "|          Luis  |                |\n" +
      "|         poke   |  0        1    |\n" +
      "|                |                |\n" +
      "|  poke          |  2        3    |\n" +
      "|  Luis          |                |\n" +
      "+----------------+----------------+\n" in {
        field.setPlayerNameTo("Luis").setPlayerNameTo("Timmy").setPokemonTo(List(Some(Pokemon(Glurak)))).setPokemonTo(List(Some(Pokemon(Simsala)))).mesh() should be(
            "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 150     |     1. Glut             2. Flammenwurf           |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 130                              |     3. Biss             4. Inferno               |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "have a mesh at next step\n" +
        "+----------------+----------------+\n" +
        "|          Luis  |                |\n" +
        "|         poke   |  0        1    |\n" +
        "|                |                |\n" +
        "|  poke          |  2        3    |\n" +
        "|  Luis          |                |\n" in {
        field.setPlayerNameTo("Luis").setPlayerNameTo("Timmy").setPokemonTo(List(Some(Pokemon(Glurak)))).setPokemonTo(List(Some(Pokemon(Simsala)))).setNextTurn().mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
          "|                                         Luis     |                                                  |\n"+
          "|                               Glurak HP: 150     |     1. Konfusion        2. Psychoklinge          |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|     Simsala HP: 130                              |     3. Psychokinese     4. Eishieb               |\n"+
          "|     Timmy                                        |                                                  |\n"+
          "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      val newField = Field(50, PokePlayer("Luis",1, PokePack(List(Some(Pokemon(Glurak)), Some(Pokemon(Simsala))))), PokePlayer("Timmy", 2, PokePack(List(Some(Pokemon(Simsala)), Some(Pokemon(Turtok)))))).setNextTurn().attack(0)
      "attack" in{
        newField.toString should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 140     |     1. Konfusion        2. Psychoklinge          |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 130                              |     3. Psychokinese     4. Eishieb               |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "next attack" in{
        newField.setNextTurn().attack(2).mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 140     |     1. Glut             2. Flammenwurf           |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 120                              |     3. Biss             4. Inferno               |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "attack inv" in {
        newField.attackInv(2).mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
          "|                                         Luis     |                                                  |\n"+
          "|                               Glurak HP: 170     |     1. Konfusion        2. Psychoklinge          |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|                                                  |                                                  |\n"+
          "|     Simsala HP: 130                              |     3. Psychokinese     4. Eishieb               |\n"+
          "|     Timmy                                        |                                                  |\n"+
          "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "attack inv 2" in {
        newField.setNextTurn().attackInv(2).mesh() should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 140     |     1. Glut             2. Flammenwurf           |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Simsala HP: 140                              |     3. Biss             4. Inferno               |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
      "get Current Pokemons" in {
        newField.getCurrentPokemons should be(List(Pokemon(Simsala), Pokemon(Turtok)))
      }
      "change Pokemon" in {
        newField.changePokemon(1).toString should be(
          "+--------------------------------------------------+--------------------------------------------------+\n"+
            "|                                         Luis     |                                                  |\n"+
            "|                               Glurak HP: 140     |     1. Aquaknarre       2. Biss                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|                                                  |                                                  |\n"+
            "|     Turtok HP: 130                               |     3. Hydropumpe       4. Matschbombe           |\n"+
            "|     Timmy                                        |                                                  |\n"+
            "+--------------------------------------------------+--------------------------------------------------+\n"
        )
      }
    }
  }
}