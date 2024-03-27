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

  def printAttackDetails( attackNumber: Int ): Pokemon => String = pokemon => {
    val attacks = pokemon.pType.attacks
    " " * calcSpace( 0.1 ) + attackNumber + ". " + attacks(
      attackNumber - 1
    ).name + " " * (
      calcSpace( 0.4, attacks( attackNumber - 1 ).name ) - 3
    )
  }

  def printTopAttacksOf( pokemon: Pokemon ): String = {
    val printAttack1 = printAttackDetails( 1 )
    val printAttack2 = printAttackDetails( 2 )
    "|" + printAttack1( pokemon ) + printAttack2( pokemon ) + "|\n"
  }

  def printBottomAttacksOf( pokemon: Pokemon ): String = {
    val printAttack3 = printAttackDetails( 3 )
    val printAttack4 = printAttackDetails( 4 )
    "|" + printAttack3( pokemon ) + printAttack4( pokemon ) + "|\n"
  }
