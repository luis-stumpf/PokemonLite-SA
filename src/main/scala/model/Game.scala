package de.htwg.se.pokelite
package model

import model.states.InitState
import model.State
import model.PokemonArt
import model.Field

import model.*
import model.PokemonType.{Glurak, Simsala,Brutalanda,Bisaflor,Turtok}

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
                turn: Int = 1,
                winner: Option[PokePlayer] = None){
  def copy(
          state:State = state,
          player1: Option[PokePlayer] = player1,
          player2: Option[PokePlayer] = player2,
          turn: Int = turn,
          winner: Option[PokePlayer] = winner
          ): Game = Game(state, player1, player2, turn, winner)

  def setStateTo(newState: State): Game = copy(state = newState)

  def setNextTurn(): Game =
    if(turn == 1)
      copy(turn = 2)
    else
      copy(turn = 1)

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

    if player1.get.pokemons.contents.isEmpty then copy(player1 = Some(PokePlayer(player1.get.name,PokePack(pokeList))))
    else copy(player2 = Some(PokePlayer(player2.get.name, PokePack(pokeList), 2)))


  def attackWith(input:String): Game = this //TODO: Implement that the input gets read and chooses an attack and updates the pplayers pokemon
  
  def reverseAttackWith(input:String): Game = this //TODO: implement reverse attack with this input

  def selectPokemon(input: String): Game = this //TODO switch pokemon of current player to input

  override def toString : String = Field(50, player1.get, player2.get, turn).toString

  /*
  object AttackPlayerStrat {

    var strategy = if ( turn == player1 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      val mult = getDamageMultiplikator( player1.pokemons.contents.apply( player1.currentPoke ).get.pType.pokemonArt, player2.pokemons.contents.apply( player2.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player2 = player2.copy( pokemons = player2.pokemons.copy( player2.pokemons.contents.updated( player2.currentPoke, player2.pokemons.contents.apply( player2.currentPoke ).get.changeHp( player1.pokemons.contents.apply( player1.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) )
      if player2.checkForDead() then
        System.exit(0)
      kopie
    def strategy2(attack : Int) =
      val mult = getDamageMultiplikator( player2.pokemons.contents.apply( player2.currentPoke ).get.pType.pokemonArt, player1.pokemons.contents.apply( player1.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player1 = player1.copy( pokemons = player1.pokemons.copy( player1.pokemons.contents.updated( player1.currentPoke, player1.pokemons.contents.apply( player1.currentPoke ).get.changeHp( player2.pokemons.contents.apply( player2.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) )
      if player1.checkForDead() then
        System.exit(0)
      kopie
  }
  */
  
}
      

          

