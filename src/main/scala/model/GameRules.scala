package de.htwg.se.pokelite
package model

import model.*

val POKEPACK_SIZE = 3

def getDamageMultiplikator(pokemonArt1 : PokemonArt, pokemonArt2 : PokemonArt) : Double =
  pokemonArt1 match
    case PokemonArt.Wasser => pokemonArt2 match
      case PokemonArt.Wasser => 1
      case PokemonArt.Feuer => 1.2
      case PokemonArt.Blatt => 0.5
      case PokemonArt.Psycho => 1
    case PokemonArt.Feuer => pokemonArt2 match
      case PokemonArt.Wasser => 0.5
      case PokemonArt.Feuer => 1
      case PokemonArt.Blatt => 1.3
      case PokemonArt.Psycho => 1
    case PokemonArt.Blatt => pokemonArt2 match
      case PokemonArt.Wasser => 1.1
      case PokemonArt.Feuer => 1.3
      case PokemonArt.Blatt => 1
      case PokemonArt.Psycho => 1.2
    case PokemonArt.Psycho => pokemonArt2 match
      case PokemonArt.Wasser => 1
      case PokemonArt.Feuer => 1
      case PokemonArt.Blatt => 1
      case PokemonArt.Psycho => 0.7  
          

