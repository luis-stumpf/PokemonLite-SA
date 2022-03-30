package de.htwg.se.pokelite
package model

enum Pokemon(stringRepresentation: String, hp: Int):
  override def toString = stringRepresentation;
  def getHp = hp;
  case G extends Pokemon("Glurak", 110);
  case P extends Pokemon("Pikachu", 120);
  case B extends Pokemon("Brutalanda", 130);
  case M extends Pokemon("Mewtu", 140);
  case R extends Pokemon("Relaxo", 150);
  case S extends Pokemon("Simsala", 160);
  case Empty extends Pokemon("No pokemon", 0)