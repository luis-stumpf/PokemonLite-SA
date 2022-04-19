package de.htwg.se.pokelite
package model

enum Pokemon(stringRepresentation: String, hp: Int):
  override def toString: String = stringRepresentation
  def getHp: Int = hp
  case G extends Pokemon("G", 110)
  case P extends Pokemon("P", 120)
  case B extends Pokemon("B", 130)
  case M extends Pokemon("M", 140)
  case R extends Pokemon("R", 150)
  case S extends Pokemon("S", 160)
  case Empty extends Pokemon("No Pokemon", 10000)