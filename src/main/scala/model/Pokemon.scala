package de.htwg.se.pokelite
package model

case class Pokemon( pType: PokemonType, hp: Int ){
  def changeHp(attack: Attack): Pokemon = copy(hp = hp - attack.damage)

}

enum PokemonType(name: String, hp: Int, attacks: List[AttackType]) {
  override def toString: String = name + " HP: " + hp
  case NoPokemon extends PokemonType(name ="", hp = -1,
    attacks = List(NoAttack(), NoAttack(), NoAttack(), NoAttack()))
  {
    def toString: String = ""
  }

  case Glurak extends PokemonType(name= "Glurak", hp = 150,
    attacks = List(Attack( "Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite", 15), Attack("Tackle", 10)))

  case Simsala extends PokemonType(name = "Simsala", hp = 130,
    attacks = List(Attack("Simsala", 30), Attack("Simsala", 20), Attack("Simsala", 15), Attack("Simsala", 10)))

  case Brutalanda extends PokemonType(name = "Brutalanda", hp = 180,
    attacks = List(Attack("Flammenwurf", 30), Attack("Donnerblitz", 20), Attack("Bite", 15), Attack("Tackle", 10)))
}
end PokemonType
