package de.htwg.se.pokelite.model

case class Field(width: Int):
  def mesh (width : Int = 50, height : Int = 3, nP1 : String = "Luis", nP2 : String = "Timmy"): String = row(width) + printPlayer1(width, nP1) + printPokemonP1(width, Pokemon("Glurak", 150)) + col(width, height) + printPokemonP2(width, Pokemon("Simsala", 200)) + printPlayer2(width, nP2) + row(width)
  def row  (width : Int): String = "+"+("-"*width+"+")*2+"\n"
  def col (width : Int, height: Int): String = (("|"+" "*width)*2+"|\n")*height
  def printPlayer1 (width: Int, name: String): String = printP1left(width, name) + cleanSite(width)
  def printPlayer2 (width: Int, name: String): String = printP2left(width, name) + cleanSite(width)
  def printPokemonP1 (width: Int, pokemon: Pokemon): String = printPokeP1left(width, pokemon) + cleanSite(width)
  def printPokemonP2 (width: Int, pokemon: Pokemon): String = printPokeP2right(width, pokemon) + cleanSite(width)

  def calcSpace (width: Int, start: Double, element: String): Int = (width * start).floor.toInt - element.toString.length
  def calcSpace (width: Int, start: Double): Int = (width * start).floor.toInt
  def cleanSite (width: Int) = "|" + " " * width + "|\n"
  def printP1left (width: Int, name: String): String = "|"+" "*calcSpace(width, 0.9, name) + name + " " * calcSpace(width, 0.1)
  def printP2left (width: Int, name: String): String = "|"+" "*calcSpace(width, 0.1) + name + " " * calcSpace(width, 0.9, name)
  def printPokeP1left (width: Int, pokemon: Pokemon): String = "|"+" "*calcSpace(width, 0.9, pokemon.toString) + pokemon + " "* calcSpace(width, 0.1)
  def printPokeP2right (width: Int, pokemon: Pokemon): String = "|"+" "* calcSpace(width, 0.1) + pokemon + " "* calcSpace(width, 0.9, pokemon.toString)

  override def toString = mesh()
