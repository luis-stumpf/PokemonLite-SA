package de.htwg.se.pokelite
package model.impl.field

import model.{FieldInterface, PokePlayer, Pokemon}

case class Field(width : Int, player1 : PokePlayer , player2 : PokePlayer, isControlledBy : Int = 1) extends FieldInterface:
  override def toString: String = mesh()

  def mesh(height : Int = 3) : String = row() + printPlayer1Stats() + col( height ) + printPlayer2Stats() + row()

  def row() : String = "+" + ( "-" * width + "+" ) * 2 + "\n"

  def col(height : Int) : String = ( ( "|" + " " * width ) * 2 + "|\n" ) * height

  def printPlayer1Stats() : String = printTopPlayer() + cleanSite() + printTopPokemon()

  def printPlayer2Stats() : String = printBottomPokemon() + printBottomPlayer() + cleanSite()

  def calcSpace(start : Double, element : String) : Int = ( width * start ).floor.toInt - element.length

  def calcSpace(start : Double) : Int = ( width * start ).floor.toInt

  def cleanSite() : String = "|" + " " * width + "|\n"

  def printTopPlayer() : String = "|" + " " * calcSpace( 0.9, player1.name ) + player1.name + " " * calcSpace( 0.1 )

  def printTopPokemon() : String = "|" + " " * calcSpace( 0.9, player1.pokemons.contents.apply( player1.currentPoke ).map( _.toString ).getOrElse( "" ) ) + player1.pokemons.contents.apply( player1.currentPoke ).map( _.toString ).getOrElse( "" ) + " " * calcSpace( 0.1 ) + printTopAttacks()

  def printBottomPlayer() : String = "|" + " " * calcSpace( 0.1 ) + player2.name + " " * calcSpace( 0.9, player2.name )

  def printBottomPokemon() : String = "|" + " " * calcSpace( 0.1 ) + player2.pokemons.contents.apply( player2.currentPoke ).map( _.toString ).getOrElse( "" ) + " " * calcSpace( 0.9, player2.pokemons.contents.apply( player2.currentPoke ).map( _.toString ).getOrElse( "" ) ) + printBottomAttacks()

  def printTopAttacks() : String = if ( isControlledBy == 1 ) printTopAttacksOf( player1.pokemons.contents.apply( player1.currentPoke ) ) else printTopAttacksOf( player2.pokemons.contents.apply( player2.currentPoke ) )

  def printBottomAttacks() : String = if ( isControlledBy == 1 ) printBottomAttacksOf( player1.pokemons.contents.apply( player1.currentPoke ) ) else printBottomAttacksOf( player2.pokemons.contents.apply( player2.currentPoke ) )

  def printTopAttacksOf(pokemon : Option[ Pokemon ]) : String =
    if ( pokemon.isDefined )
      "|" + " " * calcSpace( 0.1 ) + "1. " + pokemon.get.pType.attacks.head.name + " " * ( calcSpace( 0.4, pokemon.get.pType.attacks.head.name ) - 3 ) +
        "2. " + pokemon.get.pType.attacks.apply( 1 ).name + " " * ( calcSpace( 0.5, pokemon.get.pType.attacks.apply( 1 ).name ) - 3 ) + "|\n"
    else cleanSite()

  def printBottomAttacksOf(pokemon : Option[ Pokemon ]) : String =
    if ( pokemon.isDefined )
      "|" + " " * calcSpace( 0.1 ) + "3. " + pokemon.get.pType.attacks.apply( 2 ).name + " " * ( calcSpace( 0.4, pokemon.get.pType.attacks.apply( 2 ).name ) - 3 ) +
        "4. " + pokemon.get.pType.attacks.apply( 3 ).name + " " * ( calcSpace( 0.5, pokemon.get.pType.attacks.apply( 3 ).name ) - 3 ) + "|\n"
    else cleanSite()


