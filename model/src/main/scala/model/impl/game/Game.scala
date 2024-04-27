package model.impl.game

import model.*
import model.PokemonType.*
import model.impl.pokePlayer.PokePlayer
import model.State.*

import com.google.inject.Inject
import play.api.libs.json.{ JsValue, Json }

import scala.util.{ Failure, Success, Try }
import scala.xml.Node
import util.*

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
      // player1 = PokePlayer.fromJson( ( json \ "player1" ).get ),
      player1 =
        ( json \ "player1" ).asOpt[JsValue].flatMap( PokePlayer.fromJson ),
      // player2 = PokePlayer.fromJson( ( json \ "player2" ).get ),
      player2 =
        ( json \ "player2" ).asOpt[JsValue].flatMap( PokePlayer.fromJson ),
      turn = ( json \ "turn" ).as[Int]
    )

  def isIngame( state: State ): Boolean =
    state match
      case FightingState      => true
      case DesicionState      => true
      case SwitchPokemonState => true
      case GameOverState      => true
      case _                  => false

  val damageMultipliers: Map[( PokemonArt, PokemonArt ), Double] = Map(
    ( PokemonArt.Wasser, PokemonArt.Wasser ) -> 1,
    ( PokemonArt.Wasser, PokemonArt.Feuer ) -> 1.2,
    ( PokemonArt.Wasser, PokemonArt.Blatt ) -> 0.5,
    ( PokemonArt.Wasser, PokemonArt.Psycho ) -> 1,
    ( PokemonArt.Feuer, PokemonArt.Wasser ) -> 0.5,
    ( PokemonArt.Feuer, PokemonArt.Feuer ) -> 1,
    ( PokemonArt.Feuer, PokemonArt.Blatt ) -> 1.3,
    ( PokemonArt.Feuer, PokemonArt.Psycho ) -> 1,
    ( PokemonArt.Blatt, PokemonArt.Wasser ) -> 1.1,
    ( PokemonArt.Blatt, PokemonArt.Feuer ) -> 1.3,
    ( PokemonArt.Blatt, PokemonArt.Blatt ) -> 1,
    ( PokemonArt.Blatt, PokemonArt.Psycho ) -> 1.2,
    ( PokemonArt.Psycho, PokemonArt.Wasser ) -> 1,
    ( PokemonArt.Psycho, PokemonArt.Feuer ) -> 1,
    ( PokemonArt.Psycho, PokemonArt.Blatt ) -> 1,
    ( PokemonArt.Psycho, PokemonArt.Psycho ) -> 0.7
  )

  def calculateDamageMultiplicator(
    pokemonArt1: PokemonArt,
    pokemonArt2: PokemonArt
  ): Double =
    damageMultipliers.getOrElse( ( pokemonArt1, pokemonArt2 ), 1.0 )

}

case class Game(
  state: State = InitState,
  player1: Option[PokePlayer] = None,
  player2: Option[PokePlayer] = None,
  turn: Int = 1,
  winner: Option[PokePlayer] = None
) extends GameInterface {

  @Inject
  def this() = this( state = InitState, turn = 1 )

  def toXML: Node =
    <Game>
        {state.toXML}
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
    "player1" -> Json.toJson( player1.map( _.toJson ).getOrElse( Json.obj() ) ),
    "player2" -> Json.toJson( player2.map( _.toJson ).getOrElse( Json.obj() ) ),
    "turn" -> Json.toJson( turn ),
    "winner" -> Json.toJson( winner.map( _.toJson ).getOrElse( Json.obj() ) )
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
    checkForValidNameInput( name ).flatMap(
      assignTheCorrectPlayerA
    ) // higher order function

  def removePlayer(): Game =
    if player2.nonEmpty then
      copy( player2 = None, state = InitPlayerState, turn = 2 )
    else copy( player1 = None, state = InitPlayerState, turn = 1 )

  def interpretPokemonSelectionFrom( string: String ): Try[Game] =
    getPokemonListFrom( string ).flatMap( assignTheCorrectPlayerA )

  // option match
  def removePokemonFromPlayer(): Game =
    player2.flatMap( _.pokemons.contents.head ) match {
      case Some( pokemon ) =>
        copy(
          player2 =
            player2.map( p => PokePlayer( p.name, PokePack( List( None ) ) ) ),
          turn = 2,
          state = InitPlayerPokemonState
        )
      case None =>
        copy(
          player1 =
            player1.map( p => PokePlayer( p.name, PokePack( List( None ) ) ) ),
          turn = 1,
          state = InitPlayerPokemonState
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
    case ( Some( p1 ), Some( p2 ), 1 ) =>
      p1.currentPokemon.exists( _.isDead )
    case ( Some( p1 ), Some( p2 ), 2 ) =>
      p2.currentPokemon.exists( _.isDead )
    case _ => false
  }

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
                state = DesicionState
              )
            )
          else
            Success(
              copy(
                player2 = player2.map( _.setCurrentPokeTo( selection ) ),
                turn = 1,
                state = DesicionState
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

  private def assignTheCorrectPlayerA( name: String ): Try[Game] =
    ( player1, player2 ) match {
      case ( _, Some( _ ) ) if player1.isEmpty =>
        Failure( HorriblePlayerNameError )
      case ( None, _ ) =>
        Success(
          copy(
            state = InitPlayerState,
            player1 = Some( PokePlayer( name ) ),
            turn = 2
          )
        )
      case _ =>
        Success(
          copy(
            state = InitPlayerPokemonState,
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
            state = InitPlayerPokemonState
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
            state = DesicionState
          )
        )
      case _ => Failure( HorriblePokemonSelectionError )
    }
  }

  private def checkForValidNameInput( string: String ): Try[String] =
    if (string.isEmpty)
      return Failure( NoInput )
    else if (string.length > Game.maxPlayerNameLength)
      return Failure( NameTooLong( string ) )
    Success( string )

  object Attack {

    val theCorrectPlayerWith =
      if turn == 1 then p1_attacks_p2 else p2_attacks_p1

    def p1_attacks_p2( attackNumber: Int ): Option[Game] =
      for {
        p1Type <- player1.map( _.currentPokemonType )
        p2Type <- player2.map( _.currentPokemonType )
        p2 <- player2
        damage <- player1.flatMap( _.currentPokemonDamageWith( attackNumber ) )
        mult = Game.calculateDamageMultiplicator( p1Type, p2Type )
        updatedP2 = p2.reduceHealthOfCurrentPokemon( damage * mult )
        updatedGame = copy( player2 = Some( updatedP2 ), turn = 2 )
        winnerGame = updatedGame.setWinner()
      } yield winnerGame

    def p2_attacks_p1( attackNumber: Int ): Option[Game] =
      for {
        p1Type <- player1.map( _.currentPokemonType )
        p2Type <- player2.map( _.currentPokemonType )
        p1 <- player1
        damage <- player2.flatMap( _.currentPokemonDamageWith( attackNumber ) )
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
      val multiplikator = for {
        p1 <- player1
        p2 <- player2
      } yield Game.calculateDamageMultiplicator(
        p1.currentPokemonType,
        p2.currentPokemonType
      )

      val damage = player1
        .flatMap { p1 =>
          p1.currentPokemon.map { pokemon =>
            pokemon.damageOf( attackNumber ) * multiplikator.getOrElse( 1.0 )
          }
        }
        .getOrElse( 0.0 )

      copy(
        player2 = Some( player2.get.increaseHealthOfCurrentPokemon( damage ) ),
        state = FightingState,
        turn = 1
      )

    def p2_attacked_p1( attackNumber: Int ): Game =
      val mult = for {
        p2 <- player2
        p1 <- player1
      } yield Game.calculateDamageMultiplicator(
        p2.currentPokemonType,
        p1.currentPokemonType
      )

      val damage = player2
        .flatMap { p2 =>
          p2.currentPokemon.map { pokemon =>
            pokemon.damageOf( attackNumber ) * mult.getOrElse( 1.0 )
          }
        }
        .getOrElse( 0.0 )

      copy(
        player1 = Some( player1.get.increaseHealthOfCurrentPokemon( damage ) ),
        state = FightingState,
        turn = 2
      )

  }

}
