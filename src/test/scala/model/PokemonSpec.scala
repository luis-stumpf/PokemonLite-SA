package model

import model.PokemonType.Glurak

import play.api.libs.json.Json
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PokemonSpec extends AnyWordSpec {
  "A Pokemon" should {
    val pokemon = Pokemon( Glurak )
    "have a String of form ''" in {
      pokemon.toString should be( "Glurak HP: 150" )
      pokemon.reduceHP( 0 ) should be( Pokemon( Glurak, 150 ) )
      pokemon.reduceHP( 150 ).toString should be( "Glurak is dead" )
    }
    "attackInv" in {
      pokemon.increaseHP( 20.0 ).toString should be( "Glurak HP: 150" )
    }
    "increase HP correctly" in {
      pokemon.increaseHP( 20.0 ) should be( pokemon )
    }
    "get its HP" in {
      pokemon.getHp should be( 150 )
    }
    "A Pokemon from XML" should {
      "be properly parsed" in {
        val xml = <pokemon>
          <pType>Glurak</pType> <hp>150</hp> <isDead>false</isDead>
        </pokemon>
        val parsedPokemon = Pokemon.fromXML( xml ).get
        parsedPokemon shouldBe Pokemon( PokemonType.Glurak )
      }
    }

    "A Pokemon from JSON" should {
      "be properly parsed" in {
        val json =
          Json.obj( "pType" -> "Glurak", "hp" -> 150, "isDead" -> false )
        val parsedPokemon = Pokemon.fromJson( json ).get
        parsedPokemon shouldBe Pokemon( PokemonType.Glurak )
      }
    }

    "A Pokemon's JSON representation" should {
      "be correctly formed" in {
        val pokemon = Pokemon( PokemonType.Glurak )
        val json = pokemon.toJson
        json.toString shouldBe """{"pType":"Glurak","hp":150,"isDead":false,"maxHp":150}"""
      }
    }
  }
  "A Glurak" should {
    val pokemon = PokemonType.Glurak
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be( "Glurak HP: 150" )
      pokemon.attacks.apply( 0 ) should be( Attack( "Glut", 20 ) )
    }
  }
  "A Simsala" should {
    val pokemon = PokemonType.Simsala
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be( "Simsala HP: 130" )
      pokemon.attacks.apply( 0 ) should be( Attack( "Konfusion", 10 ) )
    }
  }
  "A Brutalanda" should {
    val pokemon = PokemonType.Brutalanda
    "have a String of form 'name HP: Int'" in {
      pokemon.toString should be( "Brutalanda HP: 170" )
    }
  }
}

class PokemonTypeSpec extends AnyWordSpec {
  "PokemonType" when {
    "retrieving string representation" should {
      "return correct string" in {
        PokemonType.Glurak.toString shouldBe "Glurak HP: 150"
      }
    }

    "generating JSON representation" should {
      "return proper JSON object" in {
        val json = PokemonType.Glurak.toJson
        json.toString shouldBe """{"name":"Glurak","hp":150,"attacks":[{"name":"Glut","damage":20},{"name":"Flammenwurf","damage":60},{"name":"Biss","damage":10},{"name":"Inferno","damage":30}],"pokemonArt":"Feuer"}"""
      }
    }
  }
}

class PokemonArtSpec extends AnyWordSpec {
  "PokemonArt" when {
    "retrieving string representation" should {
      "return correct string for Wasser" in {
        PokemonArt.Wasser.toString shouldBe "Wasser"
      }

      "return correct string for Feuer" in {
        PokemonArt.Feuer.toString shouldBe "Feuer"
      }

      "return correct string for Blatt" in {
        PokemonArt.Blatt.toString shouldBe "Blatt"
      }

      "return correct string for Psycho" in {
        PokemonArt.Psycho.toString shouldBe "Psycho"
      }
    }
  }
}
