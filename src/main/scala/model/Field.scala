package de.htwg.se.pokelite.model

case class Field(width: Int, nP1: String, nP2: String, nP1P: PokemonType, nP2P: PokemonType):
  def mesh (width : Int = 50, height : Int = 3): String = row(width) + printPlayer1(width, nP1) + printPokemonP1(width, nP1P) + col(width, height) + printPokemonP2(width, nP2P) + printPlayer2(width, nP2) + row(width)
  def row (width : Int): String = "+"+("-"*width+"+")*2+"\n"
  def col (width : Int, height: Int): String = (("|"+" "*width)*2+"|\n")*height
  def printPlayer1 (width: Int, name: String): String = printP1left(width, name) + cleanSite(width)
  def printPlayer2 (width: Int, name: String): String = printP2left(width, name) + cleanSite(width)
  def printPokemonP1 (width: Int, pokemon: PokemonType): String = printPokeP1left(width, pokemon) + cleanSite(width)
  def printPokemonP2 (width: Int, pokemon: PokemonType): String = printPokeP2right(width, pokemon) + cleanSite(width)

  def calcSpace (width: Int, start: Double, element: String): Int = (width * start).floor.toInt - element.toString.length
  def calcSpace (width: Int, start: Double): Int = (width * start).floor.toInt
  def cleanSite (width: Int): String = "|" + " " * width + "|\n"
  def printP1left (width: Int = 50, name: String): String = "|"+" "*calcSpace(width, 0.9, name) + name + " " * calcSpace(width, 0.1)
  def printP2left (width: Int = 50, name: String): String = "|"+" "*calcSpace(width, 0.1) + name + " " * calcSpace(width, 0.9, name)
  def printPokeP1left (width: Int, pokemon: PokemonType): String = "|"+" "*calcSpace(width, 0.9, pokemon.toString) + pokemon + " "* calcSpace(width, 0.1)
  def printPokeP2right (width: Int, pokemon: PokemonType): String = "|"+" "* calcSpace(width, 0.1) + pokemon + " "* calcSpace(width, 0.9, pokemon.toString)

  def setP1(name: String): Field = copy(nP1 = name)
  def setP2(name: String): Field = copy(nP2 = name)
  def setP1P(pokemon: PokemonType): Field = copy(nP1P = pokemon)
  def setP2P(pokemon: PokemonType): Field = copy(nP2P = pokemon)
  override def toString = mesh()
