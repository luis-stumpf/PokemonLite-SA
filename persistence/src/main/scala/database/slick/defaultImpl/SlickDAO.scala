package database.slick.defaultImpl

import database.DAOInterface
import database.slick.SlickBase
import scala.concurrent.duration.DurationInt
import database.slick.defaultImpl.tables.GameTable
import database.slick.defaultImpl.tables.PlayerTable
import slick.jdbc.PostgresProfile.api._
import scala.util.Try
import model.GameInterface
import scala.concurrent.Await
import com.google.common.collect.Table
import scala.concurrent.duration.Duration
import scala.util.Success
import scala.concurrent.Future
import scala.util.Failure
import slick.lifted.TableQuery
import slick.lifted.TableQueryMacroImpl
import database.slick.defaultImpl.tables.TestTable
import model.impl.pokePlayer.PokePlayer
import model.PokePack
import play.api.libs.json.Json
import model.impl.game.Game
import model.State

object SlickDAO extends DAOInterface with SlickBase:

  init(
    DBIO.seq(
      gameTable.schema.dropIfExists,
      playerTable.schema.dropIfExists,
      playerTable.schema.createIfNotExists,
      gameTable.schema.createIfNotExists
    )
  )

  override def save( game: GameInterface ): Try[Unit] = {
    println( "Saving game" )

    val player1Id = game.player1 match {
      case Some( player ) => insertPlayer( player )
      case None           => -1
    }
    val player2Id = game.player2 match {
      case Some( player ) => insertPlayer( player )
      case None           => -1
    }
    val winnerId = game.winner match {
      case Some( player ) => insertPlayer( player )
      case None           => -1
    }

    val gameId = ( player1Id, player2Id, winnerId ) match {
      case ( -1, -1, -1 ) =>
        insertGame( game, None, None, None )
      case ( p1Id, -1, -1 ) =>
        insertGame( game, Some( p1Id ), None, None )
      case ( p1Id, p2Id, -1 ) =>
        insertGame( game, Some( p1Id ), Some( p2Id ), None )
      case ( p1Id, p2Id, wId ) =>
        insertGame( game, Some( p1Id ), Some( p2Id ), Some( wId ) )
    }

    println( "Game saved id: " + gameId )
    Success( () )
  }

  override def load( gameId: Option[Int] = None ): Try[GameInterface] = {
    println( "Loading game" )
    val game =
      Await.result(
        db.run( searchGameByIdOrCurrent( gameId ).result ),
        5.seconds
      )
    val state = game.head._2
    val player1Id = game.head._3
    val player2Id = game.head._4
    val turn = game.head._5
    val winnerId = game.head._6

    val player1 = searchPlayer( player1Id )
    val player2 = searchPlayer( player2Id )
    val winner = searchPlayer( winnerId )

    Success( Game( State.fromString( state ), player1, player2, turn, winner ) )
  }

  override def update( gameId: Option[Int], game: GameInterface ): Try[Unit] = {
    println( "Updating game slick" )
    val currentGame =
      Await.result(
        db.run( searchGameByIdOrCurrent( gameId ).result ),
        5.seconds
      )

    val player1Id = ( currentGame.head._3, game.player1 ) match {
      case ( Some( id ), Some( p ) ) => Some( updatePlayer( id, p ) )
      case ( None, Some( p ) )       => Some( insertPlayer( p ) )
      case ( Some( id ), None )      => deletePlayer( id ); None
      case ( None, None )            => None
    }
    val player2Id = ( currentGame.head._4, game.player2 ) match {
      case ( Some( id ), Some( p ) ) => Some( updatePlayer( id, p ) )
      case ( None, Some( p ) )       => Some( insertPlayer( p ) )
      case ( Some( id ), None )      => deletePlayer( id ); None
      case ( None, None )            => None
    }

    val winnerId = ( currentGame.head._6, game.winner ) match {
      case ( Some( id ), Some( p ) ) => Some( updatePlayer( id, p ) )
      case ( None, Some( p ) )       => Some( insertPlayer( p ) )
      case ( Some( id ), None )      => deletePlayer( id ); None
      case ( None, None )            => None
    }

    val updateAction = gameTable
      .filter( _.id === currentGame.head._1 )
      .map( g => ( g.state, g.player1, g.player2, g.turn, g.winner ) )
      .update(
        ( game.state.toString(), player1Id, player2Id, game.turn, winnerId )
      )

    Await.result( db.run( updateAction ), 5.seconds )
    Success( () )
  }

  override def delete( gameId: Option[Int] ): Try[Unit] = {
    println( "Deleting game" )
    val currentGame = Await.result(
      db.run( searchGameByIdOrCurrent( gameId ).result ),
      5.seconds
    )

    val deleteAction = gameTable.filter( _.id === currentGame.head._1 ).delete

    Await.result( db.run( deleteAction ), 5.seconds )

    def deletePlayer( playerId: Int ): Int = {
      Await.result(
        db.run( playerTable.filter( _.id === playerId ).delete ),
        5.seconds
      )
    }

    currentGame.head._3 match {
      case Some( id ) => deletePlayer( id )
      case None       => ()
    }
    currentGame.head._4 match {
      case Some( id ) => deletePlayer( id )
      case None       => ()
    }
    currentGame.head._6 match {
      case Some( id ) => deletePlayer( id )
      case None       => ()
    }

    Success( () )
  }

  private def testTable =
    TableQuery[TestTable]( new TestTable( _ ) )

  private def gameTable = TableQuery[GameTable]( new GameTable( _ ) )

  private def playerTable = new TableQuery[PlayerTable]( new PlayerTable( _ ) )

  private def insertPlayer( player: PokePlayer ): Int = {

    val insertAction =
      ( playerTable returning playerTable.map(
        _.id
      ) ) += ( 0, player.name, player.pokemons.toJson
        .toString(), player.currentPoke )
    Await.result( db.run( insertAction ), 5.seconds )
  }

  private def insertGame(
    game: GameInterface,
    p1Id: Option[Int],
    p2Id: Option[Int],
    winnerId: Option[Int]
  ): Int = {
    val insertAction = ( gameTable returning gameTable.map( _.id ) ) +=
      ( 0, game.state
        .toString(), p1Id, p2Id, game.turn, winnerId )

    Await.result( db.run( insertAction ), 5.seconds )
  }

  private def searchPlayer( playerId: Option[Int] ): Option[PokePlayer] = {
    playerId match {
      case Some( id ) =>
        val player = Await.result(
          db.run( playerTable.filter( _.id === id ).result ),
          5.seconds
        )
        Some(
          PokePlayer(
            player.head._2,
            PokePack.fromJson( Json.parse( player.head._3 ) ),
            player.head._4
          )
        )
      case None => None
    }
  }

  private def searchGameByIdOrCurrent( gameId: Option[Int] ) = {
    gameId match {
      case Some( id ) => gameTable.filter( _.id === id )
      case None       => gameTable.filter( _.id === gameTable.map( _.id ).max )
    }
  }

  private def updatePlayer( playerId: Int, newPlayer: PokePlayer ): Int = {
    Await.result(
      db.run(
        playerTable
          .filter( _.id === playerId )
          .map( p => ( p.name, p.pokemons, p.currentPoke ) )
          .update(
            (
              newPlayer.name,
              newPlayer.pokemons.toJson.toString(),
              newPlayer.currentPoke
            )
          )
      ),
      5.seconds
    )
  }

  private def deletePlayer( playerId: Int ): Int = {
    Await.result(
      db.run( playerTable.filter( _.id === playerId ).delete ),
      5.seconds
    )
  }
