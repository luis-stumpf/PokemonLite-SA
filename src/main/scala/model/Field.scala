package de.htwg.se.pokelite
package model

import model.PokePlayer
import model.PokemonType

case class Field(width: Int, player1: PokePlayer, player2: PokePlayer, isControlledBy: Int = 1):
  def mesh(height : Int = 3): String = row() + printPlayer1Stats() + col(height) + printPlayer2Stats() + row()
  def row(): String = "+"+("-"*width+"+")*2+"\n"
  def col(height: Int): String = (("|"+" "*width)*2+"|\n")*height

  def printPlayer1Stats(): String = printTopPlayer() + cleanSite() + printTopPokemon()
  def printPlayer2Stats(): String = printBottomPokemon() + printBottomPlayer() + cleanSite()

  def calcSpace(start: Double, element: String): Int = (width * start).floor.toInt - element.length
  def calcSpace(start: Double): Int = (width * start).floor.toInt
  def cleanSite(): String = "|" + " " * width + "|\n"

  def printTopPlayer(): String = "|"+" "* calcSpace(0.9, player1.name) + player1.name + " " * calcSpace(0.1)
  def printTopPokemon(): String = "|"+" "*calcSpace(0.9, player1.pokemon.toString) + player1.pokemon + " "* calcSpace(0.1)   + printTopAttacks()
  def printBottomPlayer(): String = "|"+" "* calcSpace(0.1) + player2.name + " " * calcSpace(0.9, player2.name)
  def printBottomPokemon(): String = "|"+" "* calcSpace(0.1) + player2.pokemon + " "* calcSpace(0.9, player2.pokemon.toString)  + printBottomAttacks()

  def printTopAttacks(): String = if(isControlledBy == 1) printTopAttacksOf(player1.pokemon) else printTopAttacksOf(player2.pokemon)
  def printBottomAttacks(): String = if(isControlledBy == 1) printBottomAttacksOf(player1.pokemon) else printBottomAttacksOf(player2.pokemon)

  def printTopAttacksOf(pokemon: PokemonType): String = "|" + " " * calcSpace(0.1) + pokemon.attacks.apply(0).toString + " " *calcSpace(0.4, pokemon.attacks.apply(0).toString) +
    pokemon.attacks.apply(1).name + " " *calcSpace(0.5, pokemon.attacks.apply(1).toString)+ "|\n"
  def printBottomAttacksOf(pokemon: PokemonType): String = "|" + " " * calcSpace(0.1) + pokemon.attacks.apply(2).name + " " *calcSpace(0.4, pokemon.attacks.apply(2).toString) +
    pokemon.attacks.apply(3).name + " " *calcSpace(0.5, pokemon.attacks.apply(3).toString)+ "|\n"


  def setPlayerNameTo(newName: String): Field = if (player1.name == "") copy(player1 = player1.setPokePlayerNameTo(newName)) else copy(player2 = player2.setPokePlayerNameTo(newName))
  def setPokemonTo(newPokemon: PokemonType): Field = if (player1.pokemon == NoPokemon()) copy(player1 = player1.setPokemonTo(newPokemon)) else copy(player2 = player2.setPokemonTo(newPokemon))
  def setNextTurn(): Field = if (isControlledBy == 1) copy(isControlledBy = 2) else copy(isControlledBy = 1)
  override def toString = mesh()
