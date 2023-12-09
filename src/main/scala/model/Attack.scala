package de.htwg.se.pokelite
package model

import play.api.libs.json.{JsValue, Json}

trait AttackType {
  val name : String
  val damage : Int

  def toJson: JsValue =
    Json.obj(
      "name" -> Json.toJson(name),
      "damage" -> Json.toJson(damage)
    )
}

case class Attack( name : String, damage : Int ) extends AttackType :
  override def toString : String = name

case class NoAttack( name : String = "", damage : Int = 0 ) extends AttackType :
  override def toString : String = ""
