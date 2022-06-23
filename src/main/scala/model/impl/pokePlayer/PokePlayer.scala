package de.htwg.se.pokelite
package model.impl.pokePlayer

import model.{GameInterface, PokePack, PokePlayerInterface, Pokemon, PokemonArt}
import model.impl.pokePlayer

import com.google.inject.Inject
import de.htwg.se.pokelite.model.impl.game.Game
import scala.xml.Node


object PokePlayer {
  def fromXML(node:Node):Option[PokePlayer] =
    Some(PokePlayer(
      name = (node \\ "name").text.toString.trim,
      pokemons = PokePack.fromXML((node \\ "pokemons").head),
      currentPoke = (node \\ "currentPoke").text.toInt,
    ))
}

case class PokePlayer (name : String, pokemons : PokePack = PokePack( List( None ) ), currentPoke : Int = 0) extends PokePlayerInterface :


  @Inject
  def this() = this(name = "", pokemons = PokePack(List(None)))

  def toXML:Node =
    <PokePlayer name ={name.toString}>
      <pokemons>{pokemons.toXML}</pokemons>
      <currentPoke>{currentPoke.toString}</currentPoke>
    </PokePlayer>

  override def toString : String = name

  def setPokemonTo(newPokemons : PokePack) : PokePlayer = copy( pokemons = newPokemons )

  def setPokePlayerNameTo(newName : String) : PokePlayer = copy( name = newName )

  def setCurrentPokeTo(number : Int) : PokePlayer = copy( currentPoke = number - 1 )

  def checkForDefeat() : Boolean = pokemons.checkIfAllPokemonAreDead
  
  def getCurrentPokemonType : PokemonArt = pokemons.contents.apply( currentPoke ).get.pType.pokemonArt

  def getCurrentPokemon : Pokemon = pokemons.contents.apply( currentPoke ).get

  def currentPokemonDamageWith(attackNumber : Int) : Int = pokemons.contents.apply( currentPoke ).get.damageOf( attackNumber )

  def reduceHealthOfCurrentPokemon(amount : Double) : PokePlayer =
    copy( pokemons = PokePack(pokemons.contents.updated( currentPoke, Some(getCurrentPokemon.reduceHP( amount ) ) )))

  def increaseHealthOfCurrentPokemon(amount : Double) : PokePlayer =
    copy( pokemons = PokePack(pokemons.contents.updated( currentPoke, Some(getCurrentPokemon.increaseHP( amount ) ) )))



