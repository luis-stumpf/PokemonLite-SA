package de.htwg.se.pokelite
package model.impl.game

import model.{ Attack, PokePack, Pokemon, PokemonArt }

import de.htwg.se.pokelite.model.PokemonType.{ Glurak, Simsala }
import model.PokemonArt
import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer
import de.htwg.se.pokelite.model.states.{ FightingState, InitPlayerState, InitState }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec {
  "The Game Class" when {

    "empty" should {
      var game = Game()
      "have the InitState" in {
        assert( game.state == InitState() )
      }
      "have no players" in {
        assert( game.player1 == None && game.player2 == None )
      }
      "be able to set the state" in {
        game.setStateTo( InitPlayerState() ) should be( Game( InitPlayerState() ) )
      }
      "be able to add a Player" in {
        game.addPlayerWith( "Luis" ) should be( Game( InitState(), Some( PokePlayer( "Luis" ) ) ) )
      }
      "be able to remove a Player" in {
        game = game.addPlayerWith( "Luis" )
        game.removePlayer() should be( Game( InitState() ) )
      }
      "be able to remove a Pokemon from a Player 1" in {
        game = game.addPlayerWith( "timmy" )
        game = game.interpretPokemonSelectionFrom( "1" )
        game.removePokemonFromPlayer() should be( Game( InitState(), Some( PokePlayer( "Luis" ) ), Some( PokePlayer( "timmy" ) ) ) )
      }
      "be able to remove a Pokemon from a Player 2" in {
        game = game.interpretPokemonSelectionFrom( "1" )
        game.removePokemonFromPlayer() should be( Game( InitState(), Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ), Some( PokePlayer( "timmy" ) ) ) )
      }
      "be able to attack a player 1" in {
        game = Game( FightingState(),
          Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) ),
          Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ) )
        game = game.setNextTurn()
        game.interpretAttackSelectionFrom( "1" ) should be {
          Game( FightingState(),
            Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) ),
            Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Glurak ) ).get.reduceHP( Attack( "Konfusion", 10 ), Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Feuer ) ) ) ) ) ) ).setNextTurn()

        }
      }
      "be able to reverse attack a player 1" in {
        game = Game( FightingState(),
          Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Simsala ) ).get.reduceHP( Attack( "Konfusion", 20 ), Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Feuer ) ) ) ) ) ),
          Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ) ).setNextTurn()
        game.reverseAttackWith( "1" ) should be {
          Game( FightingState(),
            Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) ),
            Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ) ).setNextTurn()

        }
      }
      "be able to attack a player 2" in {
        game = Game( FightingState(),
          Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ),
          Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) ) )
        game.interpretAttackSelectionFrom( "1" ) should be {
          Game( FightingState(),
            Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ).get.reduceHP( Attack( "Konfusion", 10 ), Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Feuer ) ) ) ) ) ),
            Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) ) )

        }
      }
      // TODO: Testen ob AttackPlayerStrat.strategy die richtige entscheidung trifft.
      "be able to reverse attack a player 2" in {
        game = Game( FightingState(),
          Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ),
          Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Simsala ) ).get.reduceHP( Attack( "Konfusion", 20 ), Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Feuer ) ) ) ) ) ) )
        game.reverseAttackWith( "1" ) should be {
          Game( FightingState(),
            Some( PokePlayer( "Luis", PokePack( List( Some( Pokemon.apply( Glurak ) ) ) ) ) ),
            Some( PokePlayer( "Timmy", PokePack( List( Some( Pokemon.apply( Simsala ) ) ) ) ) ) )

        }
      }
    }
  }
  "The Game Object" should {

    "return a damage Multiplikator with water and water" in {
      Game.calculateDamageMultiplicator( PokemonArt.Wasser, PokemonArt.Wasser ) should be( 1 )
    }
    "return a damage Multiplikator with water and feuer" in {
      Game.calculateDamageMultiplicator( PokemonArt.Wasser, PokemonArt.Feuer ) should be( 1.2 )
    }
    "return a damage Multiplikator with water and blatt" in {
      Game.calculateDamageMultiplicator( PokemonArt.Wasser, PokemonArt.Blatt ) should be( 0.5 )
    }
    "return a damage Multiplikator with water and Psycho" in {
      Game.calculateDamageMultiplicator( PokemonArt.Wasser, PokemonArt.Psycho ) should be( 1 )
    }

    "return a damage Multiplikator with fire and water" in {
      Game.calculateDamageMultiplicator( PokemonArt.Feuer, PokemonArt.Wasser ) should be( 0.5 )
    }
    "return a damage Multiplikator with fire and feuer" in {
      Game.calculateDamageMultiplicator( PokemonArt.Feuer, PokemonArt.Feuer ) should be( 1 )
    }
    "return a damage Multiplikator with fire and blatt" in {
      Game.calculateDamageMultiplicator( PokemonArt.Feuer, PokemonArt.Blatt ) should be( 1.3 )
    }
    "return a damage Multiplikator with fire and Psycho" in {
      Game.calculateDamageMultiplicator( PokemonArt.Feuer, PokemonArt.Psycho ) should be( 1 )
    }

    "return a damage Multiplikator with blatt and water" in {
      Game.calculateDamageMultiplicator( PokemonArt.Blatt, PokemonArt.Wasser ) should be( 1.1 )
    }
    "return a damage Multiplikator with blatt and feuer" in {
      Game.calculateDamageMultiplicator( PokemonArt.Blatt, PokemonArt.Feuer ) should be( 1.3 )
    }
    "return a damage Multiplikator with blatt and blatt" in {
      Game.calculateDamageMultiplicator( PokemonArt.Blatt, PokemonArt.Blatt ) should be( 1 )
    }
    "return a damage Multiplikator with blatt and Psycho" in {
      Game.calculateDamageMultiplicator( PokemonArt.Blatt, PokemonArt.Psycho ) should be( 1.2 )
    }

    "return a damage Multiplikator with psycho and water" in {
      Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Wasser ) should be( 1 )
    }
    "return a damage Multiplikator with psycho and feuer" in {
      Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Feuer ) should be( 1 )
    }
    "return a damage Multiplikator with psycho and blatt" in {
      Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Blatt ) should be( 1 )
    }
    "return a damage Multiplikator with psycho and Psycho" in {
      Game.calculateDamageMultiplicator( PokemonArt.Psycho, PokemonArt.Psycho ) should be( 0.7 )
    }
    "have a poke Pack size set to a number" in {
      assert( Game.maxPokePackSize.isValidInt )
    }
  }

}
