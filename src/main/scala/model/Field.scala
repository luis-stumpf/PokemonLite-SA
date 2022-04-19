package de.htwg.se.pokelite.model
import scala.io.StdIn.readLine

@main def run() : Unit =
  println("Enter name of Player 1: ")
  val namePlayer1 = readLine()
  println(mesh(60, 15))
  println("Enter name of Player 2d: ")
  val namePlayer2 = readLine()



def mesh (width : Int, height : Int): String = row(width) + col(width, height) + row(width)
def row  (width : Int): String = "+"+("-"*width+"+")*2+"\n"
def col (width : Int, height: Int): String = (("|"+" "*width)*2+"|\n")*height

