package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.PokemonType.{ Brutalanda, Glurak, Simsala }
import model.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PokePlayerSpec extends AnyWordSpec {
  "PokePlayerSpec" should {
    val pokeList1 = new PokePack[ Option[ Pokemon ] ]( List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) ) )
    val pokeList2 = new PokePack[ Option[ Pokemon ] ]( List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala, 0 , true ) ) ) )
    val pokeList3 = new PokePack[ Option[ Pokemon ] ]( List( Some( Pokemon( Simsala ) ), Some( Pokemon( Simsala ) ) ) )
    val pokeListDead = new PokePack[ Option[ Pokemon ] ]( List( Some( Pokemon( Simsala, 0, true ) ),Some( Pokemon( Glurak, 0, true ) ) ) )
    val p1 = PokePlayer( "Luis", 1 )
    val p2 = PokePlayer( "Timmy", 1, pokeList2, 1 )
    val p3 = PokePlayer( "Otto", 0, pokeList3, 2 )
    val p4 = PokePlayer( "Paul",1, pokeListDead,1)
    "have a name in form 'name'" in {
      p1.toString should be( "Luis" )
      p2.toString should be( "Timmy" )
      p3.toString should be( "Otto" )
    }
    "check Pokemons" in {
      p1.pokemons should be( new PokePack[ Option[ Pokemon ] ] (List( None )) )
      p2.pokemons should be( new PokePack[ Option[ Pokemon ] ]( List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala,0,true ) ) ) ) )
      p1.currentPoke should be( 0 )
      p3.currentPoke should be( 2 )
      p1.number should be( 1 )
      p3.number should be( 0 )
    }
    "set Player Name in form new PokePlayer" in {
      p1.setPokePlayerNameTo( "Udo" ).name should be( "Udo" )
    }
    "set Pokemons to" in {
      p1.setPokemonTo( pokeList1 ) should be( p1.copy( pokemons = pokeList1 ) )
      p2.setPokemonTo( pokeList3 ) should be( p2.copy( pokemons = pokeList3 ) )
    }
    "check if at least one pokemon in the Pack is alive" in {
      p2.checkForDead() should be(false)
    }
    "check if all Pokemon in a pack are dead" in {
      p4.checkForDead() should be(true)
    }
  }
}
