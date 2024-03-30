package de.htwg.se.pokelite
package model.impl.pokePlayer

import model.*
import model.impl.game.Game
import model.impl.pokePlayer

import com.google.inject.Inject
import play.api.libs.json.{ JsValue, Json }

import scala.xml.Node

object PokePlayer {
  def fromXML( node: Node ): Option[PokePlayer] =
    Some(
      PokePlayer(
        name = ( node \\ "name" ).text.toString.trim,
        pokemons = PokePack.fromXML( ( node \\ "pokemons" ).head ),
        currentPoke = ( node \\ "currentPoke" ).text.replace( " ", "" ).toInt
      )
    )

  def fromJson( json: JsValue ): Option[PokePlayer] =
    Some(
      PokePlayer(
        name = ( json \ "name" ).get.toString.replace( "\"", "" ),
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

  def getCurrentPokemonType: PokemonArt =
    pokemons.contents( currentPoke ).pType.pokemonArt

  def getCurrentPokemon: Pokemon = pokemons.contents( currentPoke )

  def currentPokemonDamageWith( attackNumber: Int ): Int =
    pokemons.contents( currentPoke ).damageOf( attackNumber )

  def reduceHealthOfCurrentPokemon( amount: Double ): PokePlayer =
    withPokemon( _.reduceHP( amount ) )( getCurrentPokemon )

  def increaseHealthOfCurrentPokemon( amount: Double ): PokePlayer =
    withPokemon( _.increaseHP( amount ) )( getCurrentPokemon )

  def withPokemon( fn: ( Pokemon ) => Pokemon )( poke: Pokemon ): PokePlayer = {
    copy(pokemons =
      PokePack( pokemons.contents.updated( currentPoke, fn( poke ) ) )
    )
  }
