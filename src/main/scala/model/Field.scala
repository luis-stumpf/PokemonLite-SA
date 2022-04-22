package de.htwg.se.pokelite.model
import scala.io.StdIn.readLine

@main def run() : Unit =
  println(mesh(60, 1, " ", " "))
  println("Enter name of Player 1: ")
  val namePlayer1 = readLine()
  println(mesh(60, 1, namePlayer1, " "))
  println("Enter name of Player 2: ")
  val namePlayer2 = readLine()
  println(mesh(60, 15, namePlayer1, namePlayer2))


def mesh (width : Int, height : Int, nP1 : String, nP2 : String): String = row(width) + printPlayer1(width, nP1) + printPokemonP1(width, Pokemon("Glurak", 150)) + col(width, height) + printPokemonP2(width, Pokemon("Simsala", 200)) + printPlayer2(width, nP2) + row(width)
def row  (width : Int): String = "+"+("-"*width+"+")*2+"\n"
def col (width : Int, height: Int): String = (("|"+" "*width)*2+"|\n")*height
def printPlayer1 (width: Int, name: String): String = ("|"+" "*calcStart(width, 0.85, name) + name + " "* (calcStart1(width, 0.15)+ "|" + " " * width + "|\n"
def printPlayer2 (width: Int, name: String): String = ("|"+" "*((width*0.15).floor).toInt + name + " "* ((width*0.85).floor.toInt - name.length))+ "|" + " " * width + "|\n"
def printPokemonP1 (width: Int, pokemon: Pokemon): String = ("|"+" "*((width*0.85).floor.toInt - pokemon.toString.length)+ pokemon + " "* ((width*0.15).floor.toInt ))+ "|" + " " * width + "|\n"
def printPokemonP2 (width: Int, pokemon: Pokemon): String = ("|"+" "*((width*0.15).floor).toInt + pokemon + " "* ((width*0.85).floor.toInt - pokemon.toString.length))+ "|" + " " * width + "|\n"

def calcStart(width: Int, start: Double, element: String): Int = (width * start).floor.toInt - element.toString.length
def calcStart1(width: Int, start: Double): Int = (width * start).floor.toInt
