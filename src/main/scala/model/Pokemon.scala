package de.htwg.se.pokelite
package model

enum Pokemon(stringRepresentation: String, hp: Int):
  override def toString: String = stringRepresentation
  def getHp: Int = hp
  case G extends Pokemon("Glurak", 110)
  case P extends Pokemon("Pikachu", 120)
  case B extends Pokemon("Brutalanda", 130)
  case M extends Pokemon("Mewtu", 140)
  case R extends Pokemon("Relaxo", 150)
  case S extends Pokemon("Schiggy", 160)
  case Empty extends Pokemon("No Pokemon", 10000)