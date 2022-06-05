package de.htwg.se.pokelite
package model.impl.game

import model.{ GameInterface, GameRules, HorriblePlayerNameError, HorriblePokemonSelectionError, NameTooLong, NoInput, NoPlayerExists, NoPlayerToRemove, NoPokemonSelected, NotEnoughPokemonSelected, PokePack, PokePlayerInterface, Pokemon, PokemonArt, State }
import model.PokemonType.{ Bisaflor, Brutalanda, Glurak, Simsala, Turtok }
import model.impl.field.Field
import model.states.{ DesicionState, InitPlayerPokemonState, InitPlayerState, InitState }

import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer

import scala.util.{ Failure, Success, Try }

object Game extends GameRules {

  val maxPokePackSize = 3
  val maxPlayerNameLength = 20

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

case class Game(state : State = InitState(),
                player1 : Option[ PokePlayer ] = None,
                player2 : Option[ PokePlayer ] = None,
                turn : Int = 1,
                winner : Option[ PokePlayer ] = None) extends GameInterface {

  def copy(
            state : State = state,
            player1 : Option[ PokePlayer ] = player1,
            player2 : Option[ PokePlayer ] = player2,
            turn : Int = turn,
            winner : Option[ PokePlayer ] = winner
          ) : Game = Game( state, player1, player2, turn, winner )

  def setStateTo(newState : State) : Game = copy( state = newState )

  def setNextTurn() : Game =
    if ( turn == 1 )
      copy( turn = 2 )
    else
      copy( turn = 1 )

  def addPlayerWith(name : String) : Try[ Game ] =

    checkForValidNameInput( name ) match
      case Failure( x ) => Failure( x )
      case Success( validPlayerName ) => assignTheCorrectPlayerA( validPlayerName )


  def removePlayer() : Game =
    if player2.nonEmpty then
      copy( player2 = None, state = InitPlayerState(), turn = 2 )
    else
      copy( player1 = None, state = InitPlayerState(), turn = 1 )

  def interpretPokemonSelectionFrom(string : String) : Try[ Game ] =

    getPokemonListFrom( string ) match
      case Failure( x ) => Failure( x )
      case Success( validListOfPokemon ) => assignTheCorrectPlayerA(validListOfPokemon)


  def removePokemonFromPlayer() : Game =
    if player2.get.pokemons.contents.head.isDefined then
      copy(
        player2 = Some( PokePlayer( player2.get.name, PokePack( List( None ) ) ) ),
        turn = 2,
        state = InitPlayerPokemonState())
    else
      copy(
        player1 = Some( PokePlayer( player1.get.name, PokePack( List( None ) ) ) ),
        turn = 1,
        state = InitPlayerPokemonState())

  def attackWith(i : String) : Game = AttackPlayerStrat.strategy( i.charAt( 0 ).asDigit - 1 )

  def reverseAttackWith(i : String) : Game = AttackInvStrat.strategy( i.charAt( 0 ).asDigit - 1 )

  def selectPokemon(input : Int) : Game =
    if turn == 1 then copy( player1 = Some( player1.get.setCurrentPokeTo( input ) ) )
    else copy( player2 = Some( player2.get.setCurrentPokeTo( input ) ) )

  private def getPokemonListFrom(string : String) : Try[ List[ Option[ Pokemon ] ] ] =
    if string.isEmpty then
      Failure( NoPokemonSelected )
    else
      val pokeList = string.toCharArray.toList.map {
        case '1' => Some( Pokemon( Glurak ) )
        case '2' => Some( Pokemon( Simsala ) )
        case '3' => Some( Pokemon( Brutalanda ) )
        case '4' => Some( Pokemon( Bisaflor ) )
        case '5' => Some( Pokemon( Turtok ) )
        case _ => None
      }
      checkSizeOf(pokeList)

  private def checkSizeOf(pokeList: List[Option[Pokemon]]) :Try[ List[ Option[ Pokemon ] ] ] =
    val validPokemonCount = pokeList.count(x => x.nonEmpty)
    if validPokemonCount < Game.maxPokePackSize then
      Failure (NotEnoughPokemonSelected(validPokemonCount))
    else
      Success(pokeList)
      // TODO: Refactor to PokePack potentiolly

  private def assignTheCorrectPlayerA(name : String) : Try[ Game ] =
    if player2.nonEmpty && player1.isEmpty then
      Failure( HorriblePlayerNameError )
    else if player1.isEmpty then
      Success( copy(
        state = InitPlayerState(),
        player1 = Some( PokePlayer( name ) ),
        turn = 2) )
    else
      Success( copy(
        state = InitPlayerPokemonState(),
        player2 = Some( PokePlayer( name ) ),
        turn = 1) )

  private def assignTheCorrectPlayerA(listOfPokemon : List[ Option[ Pokemon ] ]) : Try[ Game ] =
    val pokePack = PokePack( listOfPokemon )

    if player1.get.pokemons == PokePack( List( None ) ) then
      Success( copy(
        player1 = Some( PokePlayer( player1.get.name, pokePack ) ),
        turn = 2,
        state = InitPlayerPokemonState()) )
    else if player1.get.pokemons == PokePack( List( None ) ) && player2.get.pokemons != PokePack( List( None ) ) then
      Failure( HorriblePokemonSelectionError)
    else
      Success( copy(
        player2 = Some( PokePlayer( player2.get.name, pokePack ) ),
        turn = 1,
        state = DesicionState()) )

  private def checkForValidNameInput(string : String) : Try[ String ] =
    if ( string.isEmpty )
      return Failure( NoInput )
    else if ( string.length > Game.maxPlayerNameLength )
      return Failure( NameTooLong( string ) )
    Success( string )

  override def toString : String = Field( 50, player1.getOrElse( PokePlayer( "", PokePack( List( None ) ) ) ), player2.getOrElse( PokePlayer( "", PokePack( List( None ) ) ) ), turn ).toString


  object AttackPlayerStrat {

    var strategy = if ( turn == 1 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      val mult = Game.getDamageMultiplikator( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt, player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player2 = Some( player2.get.copy( pokemons = player2.get.pokemons.copy( player2.get.pokemons.contents.updated( player2.get.currentPoke, player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.changeHp( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) )
      kopie

    def strategy2(attack : Int) =
      val mult = Game.getDamageMultiplikator( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt, player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player1 = Some( player1.get.copy( pokemons = player1.get.pokemons.copy( player1.get.pokemons.contents.updated( player1.get.currentPoke, player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.changeHp( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) )
      kopie
  }

  object AttackInvStrat {
    var strategy = if ( turn == 2 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      var mult = Game.getDamageMultiplikator( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt, player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt )
      copy(
        player2 = Some( player2.get.copy( pokemons = player2.get.pokemons.copy( player2.get.pokemons.contents.updated( player2.get.currentPoke, Some( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.changeHpInv( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) ) )

    def strategy2(attack : Int) =
      var mult = Game.getDamageMultiplikator( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.pokemonArt, player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.pType.pokemonArt )
      copy( player1 = Some( player1.get.copy( pokemons = player1.get.pokemons.copy( player1.get.pokemons.contents.updated( player1.get.currentPoke, Some( player1.get.pokemons.contents.apply( player1.get.currentPoke ).get.changeHpInv( player2.get.pokemons.contents.apply( player2.get.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) ) )
    //TODO: Overhaul Attack Strategys, turns dont match. Code is bloated.

  }

}