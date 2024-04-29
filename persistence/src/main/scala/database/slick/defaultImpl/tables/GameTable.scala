package database.slick.defaultImpl.tables

import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

class GameTable( tag: Tag )
    extends Table[( Int, String, Option[Int], Option[Int], Int, Option[Int] )](
      tag,
      "game"
    ):
  def * = ( id, state, player1, player2, turn, winner )

  def id = column[Int]( "id", O.PrimaryKey, O.AutoInc )

  def state = column[String]( "state" )

  def player1 = column[Option[Int]]( "player1" )

  def player2 = column[Option[Int]]( "player2" )

  def turn = column[Int]( "turn" )

  def winner = column[Option[Int]]( "winner" )

  def player1Fk =
    foreignKey( "player1_fk", player1, playerTable )( _.id.? )
  def player2Fk =
    foreignKey( "player2_fk", player2, playerTable )( _.id.? )
  def winnerFk =
    foreignKey( "winner_fk", winner, playerTable )( _.id.? )

  val playerTable = TableQuery[PlayerTable]( new PlayerTable( _ ) )
