
package de.htwg.se.pokelite
package aview


import model.*

import de.htwg.se.pokelite.controller.Controller
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TUISpec extends AnyWordSpec {
  "Tui" should {
    val tui = TUI( Controller( Field( 50, PokePlayer( "Hans", 1 ), PokePlayer( "Peter", 2 ) ) ) )
    "recognize attack input 1" in {
      tui.chooseAttack( "1" ) should be( Some( AttackMove( 0 ) ) )
    }
    "rec attach input 2" in {
      tui.chooseAttack( "2" ) should be( Some( AttackMove( 1 ) ) )
    }
    "recognize attack input 3" in {
      tui.chooseAttack( "3" ) should be( Some( AttackMove( 2 ) ) )
    }
    "rec attach input 4" in {
      tui.chooseAttack( "4" ) should be( Some( AttackMove( 3 ) ) )
    }
    "recognize a wron input" in {
      tui.chooseAttack( "a" ) should be( None  )
    }
    "recognize input z" in {
      tui.chooseAttack( "z" ) should be( None )
    }
    "recognize input y" in {
      tui.chooseAttack( "y" ) should be( None )
    }
    "recognize input s" in {
      tui.chooseAttack( "s" ) should be( None )
    }
    "recognize input q" in {
      tui.chooseAttack( "q" ) should be( None )
    }
    "choose pokemon 1" in {
      tui.inputAnalysisPokemon( "1" ) should be( Some( PokeMove( List( Some( Pokemon( PokemonType.Glurak ) ) ) ) ) )
    }
    "choose pokemon 2" in {
      tui.inputAnalysisPokemon( "2" ) should be( Some( PokeMove( List( Some( Pokemon( PokemonType.Simsala ) ) ) ) ) )
    }
    "choose pokemon 3" in {
      tui.inputAnalysisPokemon( "3" ) should be( Some( PokeMove( List( Some( Pokemon( PokemonType.Brutalanda ) ) ) ) ) )
    }
    "choose pokemon 4" in {
      tui.inputAnalysisPokemon( "4" ) should be( Some( PokeMove( List( Some( Pokemon( PokemonType.Bisaflor ) ) ) ) ) )
    }
    "choose pokemon 5" in {
      tui.inputAnalysisPokemon( "5" ) should be( Some( PokeMove( List( Some( Pokemon( PokemonType.Turtok ) ) ) ) ) )
    }
    "choose a pokemon with wrong input" in {
      tui.inputAnalysisPokemon( "c" ) should be( Some( PokeMove( List(  None ) ) ) )
    }
    "get the current player" in {
      tui.getCurrentPlayer should be (PokePlayer( "Hans", 1 ))
    }
    "get player 1" in {
      tui.ofPlayer1 should be (PokePlayer( "Hans", 1 ))
    }
    "get player 2" in {
      tui.ofPlayer2 should be (PokePlayer( "Peter", 2 ))
    }
    "get the contents of the current PokePack" in {
      tui.currentPokePackContent() should be("")
    }
  }
}
