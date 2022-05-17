package de.htwg.se.pokelite
package model

object Pokemon {
  def apply(pType : PokemonType) : Pokemon = Pokemon( pType = pType, hp = pType.hp )
}

case class Pokemon(pType : PokemonType, hp : Int) {
  def changeHp(attack : AttackType) : Pokemon = copy( hp = hp - attack.damage )
  def changeHpInv(attack : AttackType) : Pokemon = copy( hp = hp + attack.damage )

  override def toString : String = pType.name + " HP: " + hp
}

enum PokemonType(val name : String, val hp : Int, val attacks : List[ AttackType ]) {
  override def toString : String = name + " HP: " + hp

  case Glurak extends PokemonType( name = "Glurak", hp = 150,
    attacks = List( Attack( "Flammenwurf", 30 ), Attack( "Donnerblitz", 20 ), Attack( "Bite", 15 ), Attack( "Tackle", 10 ) ) )

  case Simsala extends PokemonType( name = "Simsala", hp = 130,
    attacks = List( Attack( "Simsala", 30 ), Attack( "Simsala", 20 ), Attack( "Simsala", 15 ), Attack( "Simsala", 10 ) ) )

  case Brutalanda extends PokemonType( name = "Brutalanda", hp = 180,
    attacks = List( Attack( "Flammenwurf", 30 ), Attack( "Donnerblitz", 20 ), Attack( "Bite", 15 ), Attack( "Tackle", 10 ) ) )
}
end PokemonType
