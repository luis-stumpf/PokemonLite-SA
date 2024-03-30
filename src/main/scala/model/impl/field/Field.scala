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

  def printBottomPlayer(): String = "|" + " " * calcSpace(
    0.1
  ) + player2.name + " " * calcSpace( 0.9, player2.name )

  def printTopPokemon(): String = {
    val pokemonString = player1.currentPokemon.getOrElse( "" ).toString()

    "|" + " " * calcSpace(
      0.9,
      pokemonString
    ) + pokemonString + " " * calcSpace( 0.1 ) + printTopAttacks()
  }

  def printBottomPokemon(): String = {
    val pokemonString = player2.currentPokemon.getOrElse( "" ).toString()

    "|" + " " * calcSpace( 0.1 ) + pokemonString + " " * calcSpace(
      0.9,
      pokemonString
    ) + printBottomAttacks()
  }

  def printTopAttacks(): String = {
    val currentPlayer = if (isControlledBy == 1) player1 else player2
    val currentPokeIndex = currentPlayer.currentPoke
    val pokemonContents = currentPlayer.pokemons.contents
    pokemonContents( currentPokeIndex ) match {
      case Some( pokemon ) => printAttacks( true )( pokemon )
      case None            => cleanSite()
    }
  }

  def printBottomAttacks(): String = {
    val currentPlayer = if (isControlledBy == 1) player1 else player2
    val currentPokeIndex = currentPlayer.currentPoke
    val pokemonContents = currentPlayer.pokemons.contents
    pokemonContents( currentPokeIndex ) match {
      case Some( pokemon ) => printAttacks( false )( pokemon )
      case None            => cleanSite()
    }
  }

  def printAttackDetails( attackNumber: Int ): Pokemon => String =
    pokemon => {
      val attacks = pokemon.pType.attacks
      " " * calcSpace( 0.1 ) + attackNumber + ". " + attacks(
        attackNumber - 1
      ).name + " " * (
        calcSpace( 0.4, attacks( attackNumber - 1 ).name ) - 3
      )
    }

  // closure with currying
  def printAttacks( isTop: Boolean )( pokemon: Pokemon ): String = {
    val attackRow = ( isTop, pokemon ) match {
      case ( true, _ ) =>
        "|" + printAttackDetails( 1 )( pokemon ) + printAttackDetails( 2 )(
          pokemon
        ) + "|\n"
      case ( false, _ ) =>
        "|" + printAttackDetails( 3 )( pokemon ) + printAttackDetails( 4 )(
          pokemon
        ) + "|\n"
    }
    attackRow
  }
