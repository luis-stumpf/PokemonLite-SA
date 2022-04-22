package de.htwg.se.pokelite

import de.htwg.se.pokelite.model.Field

import scala.io.StdIn.readLine


@main def run() : Unit =
  val field = new Field(3)
  println(field.mesh(60, 3, " ", " "))
  println("Enter name of Player 1: ")
  val namePlayer1 = readLine()
  println(field.mesh(60, 3, namePlayer1, " "))
  println("Enter name of Player 2: ")
  val namePlayer2 = readLine()
  println(field.mesh(60, 3, namePlayer1, namePlayer2))