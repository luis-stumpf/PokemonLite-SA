package de.htwg.se.pokelite
package model

import model.PokePlayer

case class Field(width: Int, player1: PokePlayer, player2: PokePlayer):
  def mesh(height : Int = 3): String = row() + printNameOf(player1) + printPokemonOf(player1) + col(height) + printPokemonOf(player2) + printNameOf(player2) + row()
  def row(): String = "+"+("-"*width+"+")*2+"\n"
  def col(height: Int): String = (("|"+" "*width)*2+"|\n")*height
  def printNameOf(player: PokePlayer): String = if(player.number == 1) printTop(player) + cleanSite() else printBottom(player) + cleanSite()
  def printPokemonOf(player: PokePlayer): String = if(player.number == 1) printTop(player.pokemon) else printBottom(player.pokemon)

  def calcSpace(start: Double, element: String): Int = (width * start).floor.toInt - element.length
  def calcSpace(start: Double): Int = (width * start).floor.toInt
  def cleanSite(): String = "|" + " " * width + "|\n"

  def printTop(player: PokePlayer): String = "|"+" "* calcSpace(0.9, player.name) + player.name + " " * calcSpace(0.1)
  def printBottom(player: PokePlayer): String = "|"+" "* calcSpace(0.1) + player.name + " " * calcSpace(0.9, player.name)
  def printTop(pokemon: PokemonType): String = "|"+" "*calcSpace(0.9, pokemon.toString) + pokemon + " "* calcSpace(0.1)   + printTopAttacks()
  def printBottom(pokemon: PokemonType): String = "|"+" "* calcSpace(0.1) + pokemon + " "* calcSpace(0.9, pokemon.toString)  + printBottomAttacks()

  def printTopAttacks(): String = if(player1.isInControl) printTopAttacksOf(player1.pokemon) else printTopAttacksOf(player2.pokemon)
  def printBottomAttacks(): String = if(player1.isInControl) printBottomAttacksOf(player1.pokemon) else printBottomAttacksOf(player2.pokemon)


  def printTopAttacksOf(pokemon: PokemonType): String = "|" + " " * calcSpace(0.1) + pokemon.attacks.apply(0).name + " " *calcSpace(0.4, pokemon.attacks.apply(0).toString) +
    pokemon.attacks.apply(1).name + " " *calcSpace(0.5, pokemon.attacks.apply(1).toString)+ "|\n"
  def printBottomAttacksOf(pokemon: PokemonType): String = "|" + " " * calcSpace(0.1) + pokemon.attacks.apply(2).name + " " *calcSpace(0.4, pokemon.attacks.apply(2).toString) +
    pokemon.attacks.apply(3).name + " " *calcSpace(0.5, pokemon.attacks.apply(3).toString)+ "|\n"


  def setPlayerNameTo(newName: String): Field = if (player1.name == "") copy(player1 = player1.setPokePlayerNameTo(newName)) else copy(player2 = player2.setPokePlayerNameTo(newName))
  def setPokemonTo(newPokemon: PokemonType): Field = if (player1.pokemon == NoPokemon()) copy(player1 = player1.setPokemonTo(newPokemon)) else copy(player2 = player2.setPokemonTo(newPokemon))
  override def toString = mesh()
