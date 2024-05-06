package model.impl.pokePlayer

import model.{ PokePack, Pokemon }
import model.PokemonType.{ Brutalanda, Glurak, Simsala }
import model.impl.pokePlayer

import play.api.libs.json.{ JsValue, Json }
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PokePlayerSpec extends AnyWordSpec {
  "PokePlayerSpec" should {
    val pokeList1 =
      PokePack( List( Some( Pokemon( Glurak ) ), Some( Pokemon( Glurak ) ) ) )
    val pokeList2 = PokePack(
      List( Some( Pokemon( Brutalanda ) ), Some( Pokemon( Simsala, 0, true ) ) )
    )
    val pokeList3 =
      PokePack( List( Some( Pokemon( Simsala ) ), Some( Pokemon( Simsala ) ) ) )
    val pokeListDead = PokePack(
      List(
        Some( Pokemon( Simsala, 0, true ) ),
        Some( Pokemon( Glurak, 0, true ) )
      )
    )
    val p1 = PokePlayer( "Luis" )
    val p2 = PokePlayer( "Timmy", pokeList2, 1 )
    val p3 = PokePlayer( "Otto", pokeList3, 2 )
    val p4 = PokePlayer( "Paul", pokeListDead, 1 )
    "have a name in form 'name'" in {
      p1.toString should be( "Luis" )
      p2.toString should be( "Timmy" )
      p3.toString should be( "Otto" )
    }
    "check Pokemons" in {
      p1.pokemons should be( PokePack.apply( List( None ) ) )
      p2.pokemons should be(
        PokePack(
          List(
            Some( Pokemon( Brutalanda ) ),
            Some( Pokemon( Simsala, 0, true ) )
          ),
          2
        )
      )
      p1.currentPoke should be( 0 )
      p3.currentPoke should be( 2 )
    }
    "set Player Name in form new PokePlayer" in {
      p1.setPokePlayerNameTo( "Udo" ).name should be( "Udo" )
    }
    "set Pokemons to" in {
      p1.setPokemonTo( pokeList1 ) should be( p1.copy( pokemons = pokeList1 ) )
      p2.setPokemonTo( pokeList3 ) should be( p2.copy( pokemons = pokeList3 ) )
    }
    "check if at least one pokemon in the Pack is alive" in {
      p2.checkForDefeat() should be( false )
    }
    "check if all Pokemon in a pack are dead" in {
      p4.checkForDefeat() should be( true )
    }
  }
  "The PokePlayer object" when {
    "fromXML is called with a known Node that represents a PokePlayer" should {
      "return the expected PokePlayer" in {
        val originalPokePlayer = Some(
          PokePlayer(
            name = "Player1",
            pokemons = PokePack(List(Some(Pokemon(Glurak, 150)))),
            currentPoke = 0
          )
        )
        val xml = <pokePlayer>
                    <name>Player1</name>
                    <pokemons>
                      <PokePack>
                        <contents>
                          <entry>
                            <pokemon>
                              <pType>Glurak</pType>
                              <hp>150</hp>
                              <isDead>false</isDead>
                            </pokemon>
                          </entry>
                        </contents>
                        <size>1</size>
                      </PokePack>
                    </pokemons>
                    <currentPoke>0</currentPoke>
                  </pokePlayer>
        val result = PokePlayer.fromXML(xml)

        result shouldBe originalPokePlayer
      }
    }
    "fromJson is called with a known JsValue that represents a PokePlayer" should {
      "return the expected PokePlayer" in {
        val originalPokePlayer = Some(
          PokePlayer(
            name = "Player1",
            pokemons = PokePack(List(Some(Pokemon(Glurak, 150)))),
            currentPoke = 0
          )
        )
        val json: JsValue = originalPokePlayer.get.toJson
        val result = PokePlayer.fromJson(json)

        result shouldBe originalPokePlayer
      }
    }
  }
  "The PokePlayer case class" when {
    "constructed with the @Inject constructor" should {
      "have an empty name and a PokePack with a list containing None" in {
        val pokePlayer = new PokePlayer()

        pokePlayer.name shouldBe ""
        pokePlayer.pokemons shouldBe PokePack(List(None))
      }
    }
  }
}
