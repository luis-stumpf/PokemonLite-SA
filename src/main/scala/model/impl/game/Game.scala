package de.htwg.se.pokelite
package model.impl.game

import model.*
import model.PokemonType.*
import model.impl.field.Field
import model.impl.pokePlayer.PokePlayer
import model.states.*

import com.google.inject.Inject
import play.api.libs.json.{ JsValue, Json }

import scala.util.{ Failure, Success, Try }
import scala.xml.Node

object Game extends GameRules {

  val maxPokePackSize = 3
  val maxPlayerNameLength = 20

  def fromXML( node: Node ): Game =
    Game(
      state = State.fromXML( node ),
      player1 = PokePlayer.fromXML( ( node \\ "player1" ).head ),
      player2 = PokePlayer.fromXML( ( node \\ "player2" ).head ),
      turn = ( node \\ "turn" ).text.replace( " ", "" ).toInt
    )

  def fromJson( json: JsValue ): Game =
    Game(
      state = State.fromJson( ( json \ "state" ).get ),
      player1 = PokePlayer.fromJson( ( json \ "player1" ).get ),
      player2 = PokePlayer.fromJson( ( json \ "player2" ).get ),
      turn = ( json \ "turn" ).as[Int]
    )

  def isIngame( state: State ): Boolean =
    state match
      case FightingState()      => true
      case DesicionState()      => true
      case SwitchPokemonState() => true
      case GameOverState()      => true
      case _                    => false

  def calculateDamageMultiplicator(
    pokemonArt1: PokemonArt,
    pokemonArt2: PokemonArt
  ): Double =
    pokemonArt1 match
      case PokemonArt.Wasser =>
        pokemonArt2 match
          case PokemonArt.Wasser => 1
          case PokemonArt.Feuer  => 1.2
          case PokemonArt.Blatt  => 0.5
          case PokemonArt.Psycho => 1
      case PokemonArt.Feuer =>
        pokemonArt2 match
          case PokemonArt.Wasser => 0.5
          case PokemonArt.Feuer  => 1
          case PokemonArt.Blatt  => 1.3
          case PokemonArt.Psycho => 1
      case PokemonArt.Blatt =>
        pokemonArt2 match
          case PokemonArt.Wasser => 1.1
          case PokemonArt.Feuer  => 1.3
          case PokemonArt.Blatt  => 1
          case PokemonArt.Psycho => 1.2
      case PokemonArt.Psycho =>
        pokemonArt2 match
          case PokemonArt.Wasser => 1
          case PokemonArt.Feuer  => 1
          case PokemonArt.Blatt  => 1
          case PokemonArt.Psycho => 0.7

}

case class Game(
  state: State = InitState(),
  player1: Option[PokePlayer] = None,
  player2: Option[PokePlayer] = None,
  turn: Int = 1,
  winner: Option[PokePlayer] = None
) extends GameInterface {

  @Inject
  def this() = this( state = InitState(), turn = 1 )

  def toXML: Node =
    <Game>
      <state>
        {state.toString}
      </state>
      <player1>
        {player1.map( _.toXML ).getOrElse( "None" )}
      </player1>
      <player2>
        {player2.map( _.toXML ).getOrElse( "None" )}
      </player2>
      <turn>
        {turn.toString}
      </turn>
    </Game>

  def toJson: JsValue = Json.obj(
    "state" -> Json.toJson( state.toJson ),
    "player1" -> Json.toJson(
      player1.map( _.toJson ).getOrElse( Json.toJson( "None" ) )
    ),
    "player2" -> Json.toJson(
      player2.map( _.toJson ).getOrElse( Json.toJson( "None" ) )
    ),
    "turn" -> Json.toJson( turn ),
    "winner" -> Json.toJson(
      winner.map( _.toJson ).getOrElse( Json.toJson( "None" ) )
    )
  )

  def setStateTo( newState: State ): Game = copy( state = newState )

  def hasWinner: Boolean = if winner.isDefined then true else false

  def setWinner(): Game =
    ( player1, player2 ) match {
      case ( Some( p1 ), Some( p2 ) ) =>
        if p1.checkForDefeat() then copy( winner = Some( p2 ) )
        else if p2.checkForDefeat() then copy( winner = Some( p1 ) )
        else this
      case _ => this
    }

  def setNextTurn(): Game =
    if (turn == 1)
      copy( turn = 2 )
    else
      copy( turn = 1 )

  def addPlayerWith( name: String ): Try[Game] =
    checkForValidNameInput( name ) match
      case Failure( x ) => Failure( x )
      case Success( validPlayerName ) =>
        assignTheCorrectPlayerA( validPlayerName )

  def removePlayer(): Game =
    if player2.nonEmpty then
      copy( player2 = None, state = InitPlayerState(), turn = 2 )
    else copy( player1 = None, state = InitPlayerState(), turn = 1 )

  def interpretPokemonSelectionFrom( string: String ): Try[Game] =
    getPokemonListFrom( string ) match
      case Failure( x ) => Failure( x )
      case Success( validListOfPokemon ) =>
        assignTheCorrectPlayerA( validListOfPokemon )

  def removePokemonFromPlayer(): Game =
    player2.flatMap( _.pokemons.contents.head ) match {
      case Some( pokemon ) =>
        copy(
          player2 =
            player2.map( p => PokePlayer( p.name, PokePack( List( None ) ) ) ),
          turn = 2,
          state = InitPlayerPokemonState()
        )
      case None =>
        copy(
          player1 =
            player1.map( p => PokePlayer( p.name, PokePack( List( None ) ) ) ),
          turn = 1,
          state = InitPlayerPokemonState()
        )
    }

  def interpretAttackSelectionFrom( input: String ): Try[Game] =
    if currentPokemonIsDead then return Failure( DeadPokemon )
    input match
      case "" => Failure( NoInput )
      case "1" | "2" | "3" | "4" => {
        Attack.theCorrectPlayerWith( selectedAttackFrom( input ) ) match {
          case Some( game ) => Success( game )
          case _            => Failure( NoValidAttackSelected( input ) )
        }
      }
      case _ => Failure( NoValidAttackSelected( input ) )

  private def currentPokemonIsDead = ( player1, player2, turn ) match {
    case ( Some( p1 ), Some( p2 ), 1 ) => p1.getCurrentPokemon.isDead
    case ( Some( p1 ), Some( p2 ), 2 ) => p2.getCurrentPokemon.isDead
    case _                             => false
  }

  private def bothPlayersHavePokemon = player1.isDefined && player2.isDefined

  private def selectedAttackFrom( string: String ): Int =
    string.charAt( 0 ).asDigit - 1

  def reverseAttackWith( input: String ): Game =
    ReverseAttack.theCorrectPlayerWith( selectedAttackFrom( input ) )

  def selectPokemonFrom( input: String ): Try[Game] =
    input.headOption.map( _.isDigit ) match {
      case Some( true ) =>
        val selection = input.head.asDigit
        if inputIsValidPokePack( selection ) then
          if turn == 1 then
            Success(
              copy(
                player1 = player1.map( _.setCurrentPokeTo( selection ) ),
                turn = 2,
                state = DesicionState()
              )
            )
          else
            Success(
              copy(
                player2 = player2.map( _.setCurrentPokeTo( selection ) ),
                turn = 1,
                state = DesicionState()
              )
            )
        else Failure( WrongInput( input ) )
      case _ => Failure( NoInput )
    }

  private def inputIsValidPokePack( selection: Int ) =
    selection >= 1 && selection <= Game.maxPokePackSize

  private def getPokemonListFrom( string: String ): Try[List[Option[Pokemon]]] =
    if string.isEmpty then Failure( NoPokemonSelected )
    else
      val pokeList = string.toCharArray.toList.map {
        case '1' => Some( Pokemon( Glurak ) )
        case '2' => Some( Pokemon( Simsala ) )
        case '3' => Some( Pokemon( Brutalanda ) )
        case '4' => Some( Pokemon( Bisaflor ) )
        case '5' => Some( Pokemon( Turtok ) )
        case _   => None
      }
      checkSizeOf( pokeList )

  private def checkSizeOf(
    pokeList: List[Option[Pokemon]]
  ): Try[List[Option[Pokemon]]] =
    val validPokemonCount = pokeList.count( x => x.nonEmpty )
    if validPokemonCount < Game.maxPokePackSize then
      Failure( NotEnoughPokemonSelected( validPokemonCount ) )
    else Success( pokeList )
  // TODO: Refactor to PokePack potentiolly

  private def assignTheCorrectPlayerA( name: String ): Try[Game] =
    ( player1, player2 ) match {
      case ( _, Some( _ ) ) if player1.isEmpty =>
        Failure( HorriblePlayerNameError )
      case ( None, _ ) =>
        Success(
          copy(
            state = InitPlayerState(),
            player1 = Some( PokePlayer( name ) ),
            turn = 2
          )
        )
      case _ =>
        Success(
          copy(
            state = InitPlayerPokemonState(),
            player2 = Some( PokePlayer( name ) ),
            turn = 1
          )
        )
    }

  private def assignTheCorrectPlayerA(
    listOfPokemon: List[Option[Pokemon]]
  ): Try[Game] = {
    val pokePack = PokePack( listOfPokemon )

    ( player1, player2 ) match {
      case ( Some( p1 ), _ ) if p1.pokemons == PokePack( List( None ) ) =>
        Success(
          copy(
            player1 = Some( PokePlayer( p1.name, pokePack ) ),
            turn = 2,
            state = InitPlayerPokemonState()
          )
        )
      case ( Some( p1 ), Some( p2 ) )
          if p1.pokemons == PokePack( List( None ) ) && p2.pokemons != PokePack(
            List( None )
          ) =>
        Failure( HorriblePokemonSelectionError )
      case ( _, Some( p2 ) ) =>
        Success(
          copy(
            player2 = Some( PokePlayer( p2.name, pokePack ) ),
            turn = 1,
            state = DesicionState()
          )
        )
      case _ => Failure( new Exception( "Unexpected game state" ) )
    }
  }

  private def checkForValidNameInput( string: String ): Try[String] =
    if (string.isEmpty)
      return Failure( NoInput )
    else if (string.length > Game.maxPlayerNameLength)
      return Failure( NameTooLong( string ) )
    Success( string )

  override def toString: String = Field(
    50,
    player1.getOrElse( PokePlayer( "", PokePack( List( None ) ) ) ),
    player2.getOrElse( PokePlayer( "", PokePack( List( None ) ) ) ),
    turn
  ).toString

  object Attack {

    val theCorrectPlayerWith =
      if turn == 1 then p1_attacks_p2 else p2_attacks_p1

    def p1_attacks_p2( attackNumber: Int ): Option[Game] =
      for {
        p1Type <- player1.map( _.getCurrentPokemonType )
        p2Type <- player2.map( _.getCurrentPokemonType )
        p2 <- player2
        damage <- player1.map( _.currentPokemonDamageWith( attackNumber ) )
        mult = Game.calculateDamageMultiplicator( p1Type, p2Type )
        updatedP2 = p2.reduceHealthOfCurrentPokemon( damage * mult )
        updatedGame = copy( player2 = Some( updatedP2 ), turn = 2 )
        winnerGame = updatedGame.setWinner()
      } yield winnerGame

    def p2_attacks_p1( attackNumber: Int ): Option[Game] =
      for {
        p1Type <- player1.map( _.getCurrentPokemonType )
        p2Type <- player2.map( _.getCurrentPokemonType )
        p1 <- player1
        damage <- player2.map( _.currentPokemonDamageWith( attackNumber ) )
        mult = Game.calculateDamageMultiplicator( p2Type, p1Type )
        updatedP1 = p1.reduceHealthOfCurrentPokemon( damage * mult )
        updatedGame = copy( player1 = Some( updatedP1 ), turn = 1 )
        winnerGame = updatedGame.setWinner()
      } yield winnerGame
  }

  object ReverseAttack {

    val theCorrectPlayerWith =
      if turn == 2 then p1_attacked_p2 else p2_attacked_p1

    def p1_attacked_p2( attackNumber: Int ): Game =
      val multiplikator = Game.calculateDamageMultiplicator(
        player1.get.getCurrentPokemonType,
        player2.get.getCurrentPokemonType
      )
      val damage =
        player1.get.currentPokemonDamageWith( attackNumber ) * multiplikator
      copy(
        player2 = Some( player2.get.increaseHealthOfCurrentPokemon( damage ) ),
        state = FightingState(),
        turn = 1
      )

    def p2_attacked_p1( attackNumber: Int ): Game =
      val mult = Game.calculateDamageMultiplicator(
        player2.get.getCurrentPokemonType,
        player1.get.getCurrentPokemonType
      )
      val damage = player2.get.currentPokemonDamageWith( attackNumber ) * mult
      copy(
        player1 = Some( player1.get.increaseHealthOfCurrentPokemon( damage ) ),
        state = FightingState(),
        turn = 2
      )

  }

}
