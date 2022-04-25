package de.htwg.se.pokelite
package model

case class Pokemon(name: String, hp: Int):
  override def toString: String = name + " HP: " + hp
  def changeHp(newHp: Int): Pokemon = copy(hp = newHp)
