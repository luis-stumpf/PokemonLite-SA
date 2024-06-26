package model.impl.pokePlayer

import model.*
import model.impl.game.Game
import model.impl.pokePlayer

import com.google.inject.Inject
import play.api.libs.json.{ JsValue, Json }

import scala.xml.Node
import scala.util.Success
import scala.util.Failure
import scala.util.Try

object PokePlayer {
  def fromXML( node: Node ): Option[PokePlayer] =
    if (( node \\ "player1" ).text.trim == "None") return None
    if (( node \\ "player2" ).text.trim == "None") return None
    Some(
      PokePlayer(
        name = ( node \\ "name" ).text.toString.trim,
        pokemons = PokePack.fromXML( ( node \\ "pokemons" ).head ),
        currentPoke = ( node \\ "currentPoke" ).text.replace( " ", "" ).toInt
      )
    )

  def fromJson( json: JsValue ): Option[PokePlayer] =
    if (json == Json.obj()) return None
    Some(
      PokePlayer(
        name = ( json \ "name" )
          .getOrElse( Json.parse( "" ) )
          .toString
          .replace( "\"", "" ),
        pokemons = PokePack.fromJson( ( json \\ "pokemons" ).head ),
        currentPoke = ( json \ "currentPoke" ).as[Int]
      )
    )

}

case class PokePlayer(
  name: String,
  pokemons: PokePack = PokePack( List( None ) ),
  currentPoke: Int = 0
) extends PokePlayerInterface:

  @Inject
  def this() = this( name = "", pokemons = PokePack( List( None ) ) )

  def toXML: Node =
    <PokePlayer>
      <name>
        {name}
      </name>
      <pokemons>
        {pokemons.toXML}
      </pokemons>
      <currentPoke>
        {currentPoke.toString}
      </currentPoke>
    </PokePlayer>

  def toJson: JsValue =
    Json.obj(
      "name" -> Json.toJson( name ),
      "pokemons" -> pokemons.toJson,
      "currentPoke" -> Json.toJson( currentPoke )
    )

  override def toString: String = name

  def setPokemonTo( newPokemons: PokePack ): PokePlayer =
    copy( pokemons = newPokemons )

  def setPokePlayerNameTo( newName: String ): PokePlayer =
    copy( name = newName )

  def setCurrentPokeTo( number: Int ): PokePlayer =
    copy( currentPoke = number - 1 )

  def checkForDefeat(): Boolean = pokemons.checkIfAllPokemonAreDead

  def currentPokemonType: PokemonArt =
    currentPokemon.map( _.pType.pokemonArt ).getOrElse( PokemonArt.Feuer )

  def currentPokemon: Option[Pokemon] = pokemons.contents( currentPoke )

  def currentPokemonDamageWith( attackNumber: Int ): Option[Int] =
    currentPokemon.map( _.damageOf( attackNumber ) )

  def reduceHealthOfCurrentPokemon( amount: Double ): PokePlayer =
    currentPokemon
      .map( pokemon => withPokemon( _.reduceHP( amount ) )( pokemon ) )
      .getOrElse( this )

  def increaseHealthOfCurrentPokemon( amount: Double ): PokePlayer =
    currentPokemon
      .map( pokemon => withPokemon( _.increaseHP( amount ) )( pokemon ) )
      .getOrElse( this )

  def withPokemon( fn: ( Pokemon ) => Pokemon )( poke: Pokemon ): PokePlayer = {
    copy(pokemons =
      PokePack( pokemons.contents.updated( currentPoke, Some( fn( poke ) ) ) )
    )
  }
