package de.htwg.se.pokelite.model

case class Field(width: Int, nP1: String, nP2: String, nP1P: PokemonType, nP2P: PokemonType):
  def mesh (height : Int = 3): String = row() + printPlayer1() + printPokemonP1() + col(height) + printPokemonP2() + printPlayer2() + row()
  def row (): String = "+"+("-"*width+"+")*2+"\n"
  def col (height: Int): String = (("|"+" "*width)*2+"|\n")*height
  def printPlayer1 (): String = printP1left() + cleanSite()
  def printPlayer2 (): String = printP2left() + cleanSite()
  def printPokemonP1 (): String = printPokeP1left() + cleanSite()
  def printPokemonP2 (): String = printPokeP2right() + cleanSite()

  def calcSpace (start: Double, element: String): Int = (width * start).floor.toInt - element.toString.length
  def calcSpace (start: Double): Int = (width * start).floor.toInt
  def cleanSite (): String = "|" + " " * width + "|\n"
  def printP1left (): String = "|"+" "* calcSpace(0.9, nP1) + nP1 + " " * calcSpace(0.1)
  def printP2left (): String = "|"+" "* calcSpace(0.1) + nP2 + " " * calcSpace(0.9, nP2)
  def printPokeP1left (): String = "|"+" "*calcSpace(0.9, nP1P.toString) + nP1P + " "* calcSpace(0.1)
  def printPokeP2right (): String = "|"+" "* calcSpace(0.1) + nP2P + " "* calcSpace(0.9, nP2P.toString)

  def setP1(name: String): Field = copy(nP1 = name)
  def setP2(name: String): Field = copy(nP2 = name)
  def setP1P(pokemon: PokemonType): Field = copy(nP1P = pokemon)
  def setP2P(pokemon: PokemonType): Field = copy(nP2P = pokemon)
  override def toString = mesh()
