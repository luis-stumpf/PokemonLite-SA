package de.htwg.se.pokelite
package model.impl.field

import model.impl.pokePlayer.PokePlayer
import model.{ FieldInterface, PokePlayerInterface, Pokemon }

import com.google.inject.Inject

case class Field(
  width: Int,
  player1: PokePlayerInterface,
  player2: PokePlayerInterface,
  isControlledBy: Int = 1
) extends FieldInterface:

  @Inject
  def this() = this(
    width = 30,
    player1 = PokePlayer( "" ),
    player2 = PokePlayer( "" ),
    isControlledBy = 1
  )

  override def toString: String = mesh()

  def mesh( height: Int = 3 ): String =
    row() + printPlayer1Stats() + col( height ) + printPlayer2Stats() + row()

  def row(): String = "+" + ( "-" * width + "+" ) * 2 + "\n"

  def col( height: Int ): String =
    ( ( "|" + " " * width ) * 2 + "|\n" ) * height

  def printPlayer1Stats(): String =
    printTopPlayer() + cleanSite() + printTopPokemon()

  def printPlayer2Stats(): String =
    printBottomPokemon() + printBottomPlayer() + cleanSite()

  def calcSpace( start: Double, element: String ): Int =
    ( width * start ).floor.toInt - element.length

  def calcSpace( start: Double ): Int = ( width * start ).floor.toInt

  def cleanSite(): String = "|" + " " * width + "|\n"

  def printTopPlayer(): String =
    "|" + " " * calcSpace( 0.9, player1.name ) + player1.name + " " * calcSpace(
      0.1
    )

  def printTopPokemon(): String = {
    val currentPokeIndex = player1.currentPoke
    val pokemonContents = player1.pokemons.contents
    val pokemonString =
      if (currentPokeIndex >= 0 && currentPokeIndex < pokemonContents.length) {
        pokemonContents( currentPokeIndex ).toString()
      } else {
        ""
      }
    "|" + " " * calcSpace(
      0.9,
      pokemonString
    ) + pokemonString + " " * calcSpace( 0.1 ) + printTopAttacks()
  }

  def printBottomPlayer(): String = "|" + " " * calcSpace(
    0.1
  ) + player2.name + " " * calcSpace( 0.9, player2.name )

  def printBottomPokemon(): String = {

    val currentPokeIndex = player2.currentPoke
    val pokemonContents = player2.pokemons.contents
    val pokemonString: String =
      if (currentPokeIndex >= 0 && currentPokeIndex < pokemonContents.length) {
        pokemonContents( currentPokeIndex ).toString()
      } else {
        ""
      }
    "|" + " " * calcSpace( 0.1 ) + pokemonString + " " * calcSpace(
      0.9,
      pokemonString
    ) + printBottomAttacks()
  }

  def printTopAttacks(): String = {
    val currentPlayer = if (isControlledBy == 1) player1 else player2
    val currentPokeIndex = currentPlayer.currentPoke
    val pokemonContents = currentPlayer.pokemons.contents
    if (
      currentPokeIndex >= 0 && currentPokeIndex < pokemonContents.length && pokemonContents.size > 0
    ) {
      printTopAttacksOf( pokemonContents( currentPokeIndex ) )
    } else {
      cleanSite()
    }
  }

  def printBottomAttacks(): String = {
    val currentPlayer = if (isControlledBy == 1) player1 else player2
    val currentPokeIndex = currentPlayer.currentPoke
    val pokemonContents = currentPlayer.pokemons.contents
    if (
      currentPokeIndex >= 0 && currentPokeIndex < pokemonContents.length && pokemonContents.size > 0
    ) {
      printBottomAttacksOf( pokemonContents( currentPokeIndex ) )
    } else {
      cleanSite()
    }
  }

  def printTopAttacksOf( pokemon: Pokemon ): String = {
    val attacks = pokemon.pType.attacks
    "|" + " " * calcSpace(
      0.1
    ) + "1. " + attacks.head.name + " " * ( calcSpace(
      0.4,
      attacks.head.name
    ) - 3 ) +
      "2. " + attacks.apply( 1 ).name + " " * ( calcSpace(
        0.5,
        attacks.apply( 1 ).name
      ) - 3 ) + "|\n"
  }

  def printBottomAttacksOf( pokemon: Pokemon ): String =
    val attacks = pokemon.pType.attacks
    "|" + " " * calcSpace( 0.1 ) + "3. " + pokemon.pType.attacks
      .apply( 2 )
      .name + " " * ( calcSpace(
      0.4,
      pokemon.pType.attacks.apply( 2 ).name
    ) - 3 ) +
      "4. " + pokemon.pType.attacks.apply( 3 ).name + " " * ( calcSpace(
        0.5,
        pokemon.pType.attacks.apply( 3 ).name
      ) - 3 ) + "|\n"
