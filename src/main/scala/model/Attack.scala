package de.htwg.se.pokelite
package model

trait AttackType {
  val name: String
  val damage: Int
}

case class Attack(name: String, damage: Int) extends AttackType :
  override def toString: String = name

case class NoAttack(name: String = "", damage: Int = 0) extends AttackType :
  override def toString: String = ""
