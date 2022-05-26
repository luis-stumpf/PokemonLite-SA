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
                turn: Int = 2,
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

    if player1.get.pokemons == PokePack(List( None )) then copy(player1 = Some(PokePlayer(player1.get.name, PokePack(pokeList))))
    else copy(player2 = Some(PokePlayer(player2.get.name, PokePack(pokeList))))


  def attackWith(input:String): Game =
    val i = input.charAt(0).asDigit
    AttackPlayerStrat.strategy(i-1)
  
  def reverseAttackWith(input:String): Game =
    val i = input.charAt(0).asDigit -1
    AttackInvStrat.strategy(i)

  def selectPokemon(input: Int): Game =
    if turn == 1 then copy(player1 = Some(player1.get.setCurrentPokeTo(input)))
    else copy(player2 = Some(player2.get.setCurrentPokeTo(input)))


  override def toString : String = Field(50, player1.get, player2.get, turn).toString


  object AttackPlayerStrat {

    var strategy = if ( turn == 1 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      val mult = Game.getDamageMultiplikator( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt, player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player2 = Some(player2.get.copy( pokemons = player2.get.pokemons.copy( player2.get.pokemons.contents.updated( player2.get.currentPoke, player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.changeHp( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ))
      kopie
    def strategy2(attack : Int) =
      val mult = Game.getDamageMultiplikator( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt, player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player1 = Some(player1.get.copy( pokemons = player1.get.pokemons.copy( player1.get.pokemons.contents.updated( player1.get.currentPoke, player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.changeHp( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ))
      kopie
  }

  object AttackInvStrat {
    var strategy = if ( turn == 1 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      var mult = Game.getDamageMultiplikator( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt, player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt )
      copy(
        player2 = Some(player2.get.copy( pokemons = player2.get.pokemons.copy( player2.get.pokemons.contents.updated( player2.get.currentPoke, Some( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.changeHpInv( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) ))

    def strategy2(attack : Int) =
      var mult = Game.getDamageMultiplikator( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt, player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt )
      copy( player1 = Some(player1.get.copy( pokemons = player1.get.pokemons.copy( player1.get.pokemons.contents.updated( player1.get.currentPoke, Some( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.changeHpInv( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) ))


  }

}
      

          

