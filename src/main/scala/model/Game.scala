package de.htwg.se.pokelite
package model

import model.states.InitState
import model.State
import model.PokemonArt

import de.htwg.se.pokelite.model.Pokemon

import scala.util.{ Failure, Success }


object Game {

  val pokePackSize = 3

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

}

case class Game(state: State = InitState(),
                player1: Option[PokePlayer] = None,
                player2: Option[PokePlayer] = None,
                turn: Option[PokePlayer] = None,
                winner: Option[PokePlayer] = None){
  def copy(
          state:State = state,
          player1: Option[PokePlayer] = player1,
          player2: Option[PokePlayer] = player2,
          turn: Option[PokePlayer] = turn,
          winner: Option[PokePlayer] = winner
          ): Game = Game(state, player1, player2, turn, winner)

  def setStateTo(state: State): Game = copy(state = state)

  def setNextTurn(): Game =

    if turn.isEmpty then copy(turn = player1)
    else if turn.get == player1 then copy(turn = player2)
    else copy(turn = player1)

  def addPlayer(name: String): Game =
    if player1.isEmpty then
      copy(player1 = Some(PokePlayer(name)))
    else
      copy(player2 = Some(PokePlayer(name)))

  def addPokemonToPlayer(input: String): Game =
    val pokeList : List[ Option[ Pokemon ] ] = input.toCharArray.toList.map {
      case '1' => Some( Pokemon( Glurak ) )
      case '2' => Some( Pokemon( Simsala ) )
      case '3' => Some( Pokemon( Brutalanda ) )
      case '4' => Some( Pokemon( Bisaflor ) )
      case '5' => Some( Pokemon( Turtok ) )
      case _ => None
    }

    if player1.get.pokemons.contents.isEmpty then copy(player1 = Some(PokePlayer(player1.get.name, PokePack(pokeList))))
    else copy(player2 = Some(PokePlayer(player2.get.name, PokePack(pokeList))))


  def attackWith(input:String): Game = this //TODO: Implement that the input gets read and chooses an attack and updates the pplayers pokemon
  
  def reverseAttackWith(input:String): Game = this //TODO: implement reverse attack with this input 
    

}
      

          

