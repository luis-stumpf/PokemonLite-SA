package de.htwg.se.pokelite.model

case class Field(width: Int, namePlayer1: String, namePlayer2: String, pokemonPlayer1: PokemonType, pokemonPlayer2: PokemonType):
  def mesh(height : Int = 3): String = row() + printPlayer1() + printPokemonP1(pokemonPlayer1) + col(height) + printPokemonP2() + printPlayer2() + row()
  def row(): String = "+"+("-"*width+"+")*2+"\n"
  def col(height: Int): String = (("|"+" "*width)*2+"|\n")*height
  def printPlayer1(): String = printP1left() + cleanSite()
  def printPlayer2(): String = printP2left() + cleanSite()
  def printPokemonP1(pokemon: PokemonType): String = printPokeP1left() + printAttack(pokemon)
  def printPokemonP2(): String = printPokeP2right() + cleanSite()

  def calcSpace(start: Double, element: String): Int = (width * start).floor.toInt - element.toString.length
  def calcSpace(start: Double): Int = (width * start).floor.toInt
  def cleanSite(): String = "|" + " " * width + "|\n"
  def printP1left(): String = "|"+" "* calcSpace(0.9, namePlayer1) + namePlayer1 + " " * calcSpace(0.1)
  def printP2left(): String = "|"+" "* calcSpace(0.1) + namePlayer2 + " " * calcSpace(0.9, namePlayer2)
  def printPokeP1left(): String = "|"+" "*calcSpace(0.9, pokemonPlayer1.toString) + pokemonPlayer1 + " "* calcSpace(0.1)
  def printPokeP2right(): String = "|"+" "* calcSpace(0.1) + pokemonPlayer2 + " "* calcSpace(0.9, pokemonPlayer2.toString)
  def printAttack(pokemon: PokemonType): String = "|" + pokemon.attacks.apply(0).name + "|"

  def setNameP1(name: String): Field = copy(namePlayer1 = name)
  def setNameP2(name: String): Field = copy(namePlayer2 = name)
  def setPokemonP1(pokemon: PokemonType): Field = copy(pokemonPlayer1 = pokemon)
  def setPokemonP2(pokemon: PokemonType): Field = copy(pokemonPlayer2 = pokemon)
  override def toString = mesh()
