package de.htwg.se.pokelite.model
import scala.io.StdIn.readLine

@main def run() : Unit =
  println(mesh(50, 1, " ", " "))
  println("Enter name of Player 1: ")
  val namePlayer1 = readLine()
  println(mesh(60, 15, namePlayer1, " "))
  println("Enter name of Player 2: ")
  val namePlayer2 = readLine()
  println(mesh(60, 1, namePlayer1, namePlayer2))


def mesh (width : Int, height : Int, nP1 : String, nP2 : String): String = row(width) + printPlayer1(width, nP1) + col(width, height) + printPlayer2(width, nP2) + row(width)
def row  (width : Int): String = "+"+("-"*width+"+")*2+"\n"
def col (width : Int, height: Int): String = (("|"+" "*width)*2+"|\n")*height
def printPlayer1 (width: Int, name: String): String = ("|"+" "*(width-20) + name + " "* (20 - name.length))+ "|" + " " * width + "|\n"
def printPlayer2 (width: Int, name: String): String = ("|"+" "*(width-50) + name + " "* (50 - name.length))+ "|" + " " * width + "|\n"

