package de.htwg.se.pokelite
package model

import de.htwg.se.pokelite.model.PokemonType.{Bisaflor, Brutalanda, Glurak, Simsala, Turtok}

import scala.xml.Node


object Pokemon {
  def apply(pType : PokemonType) : Pokemon = Pokemon( pType = pType, hp = pType.hp  )

  def fromXML(node:Node):Pokemon =
    Pokemon(
      pType = (node \ "pType").text.toString.trim match {
        case "Glurak" => Glurak
        case "Simsala" => Simsala
        case "Brutalanda" => Brutalanda
        case "Bisaflor" => Bisaflor
        case "Turtok" => Turtok
      },
      hp = (node \ "hp").text.toInt
    )
}

case class Pokemon(pType : PokemonType, hp : Int, isDead: Boolean = false) {
  
  def toXML:Node =
    <pokemon>
      <pType>{pType.name.toString}</pType>
      <hp>{hp.toString}</hp>
      <isDead>{isDead.toString}</isDead>
    </pokemon>
    
  def increaseHP(amount : Double) : Pokemon =
    if pType.hp <= (hp + amount) then
      copy(hp = pType.hp)
    else
      copy( hp = (hp + amount).toInt, isDead = false )

  def reduceHP(amount : Double) : Pokemon =

    val updatedHealth = (hp-amount).toInt

    if updatedHealth <= 0 then
      copy(hp = updatedHealth, isDead = true)
    else
      copy( hp = updatedHealth)

  def damageOf(attackNumber: Int) : Int = pType.attacks.apply(attackNumber).damage
  
  def getHp: Int = hp
  
  override def toString : String = if hp <= 0 then pType.name + " is dead" else pType.name + " HP: " + hp
}

enum PokemonType(val name : String, val hp : Int, val attacks : List[ AttackType ], val pokemonArt : PokemonArt) {
  override def toString : String = name + " HP: " + hp

  case Glurak extends PokemonType(
    name = "Glurak",
    hp = 150,
    attacks = List( Attack( "Glut", 20 ), Attack( "Flammenwurf", 60 ), Attack( "Biss", 10 ), Attack( "Inferno", 30 ) ),
    pokemonArt = PokemonArt.Feuer )

  case Simsala extends PokemonType(
    name = "Simsala",
    hp = 130,
    attacks = List( Attack( "Konfusion", 10 ), Attack( "Psychoklinge", 15 ), Attack( "Psychokinese", 30 ), Attack( "Eishieb", 15 ) ),
    pokemonArt = PokemonArt.Psycho )

  case Brutalanda extends PokemonType(
    name = "Brutalanda",
    hp = 170,
    attacks = List( Attack( "Fliegen", 20 ), Attack( "Drachenklaue", 30 ), Attack( "Glut", 10 ), Attack( "Flammenwurf", 35 ) ),
    pokemonArt = PokemonArt.Feuer )

  case Bisaflor extends PokemonType(
    name = "Bisaflor",
    hp = 180,
    attacks = List( Attack( "Rasierblatt", 20 ), Attack( "Tackle", 10 ), Attack( "Solarstrahl", 30 ), Attack( "Matschbombe", 15 ) ),
    pokemonArt = PokemonArt.Blatt )

  case Turtok extends PokemonType(
    name = "Turtok",
    hp = 130,
    attacks = List( Attack( "Aquaknarre", 20 ), Attack( "Biss", 15 ), Attack( "Hydropumpe", 40 ), Attack( "Matschbombe", 15 ) ),
    pokemonArt = PokemonArt.Wasser )
}
end PokemonType


