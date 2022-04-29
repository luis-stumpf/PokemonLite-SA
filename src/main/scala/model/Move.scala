package de.htwg.se.pokelite
package model

case class Move(name: String = "", pokemon: PokemonType = NoPokemon())


/*
trait Move{
  val name: String
  val pokemon: PokemonType
}


case class StrMove(name: String) extends Move{
  
}

case class PokeMove(pokemon: PokemonType) extends Move{
  
}

case class NoMove() extends Move{
  
}
*/