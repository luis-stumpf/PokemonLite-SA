package database

import model.GameInterface

import scala.util.Try

/** Interface to store current game field in database. */
trait DAOInterface:
  /** Save current game field to database.
    * @param game
    *   the field to save
    * @return
    *   Success if the field was saved successfully, Failure otherwise
    */
  def save( game: GameInterface ): Try[Unit]

  /** Load game field from database.
    * @param gameId
    *   the game id to load. If None, the last game is loaded
    * @return
    *   Success with the loaded field, Failure otherwise
    */
  def load( gameId: Option[Int] = None ): Try[GameInterface]

  /** Update game field in database.
    * @param gameId
    *   the game id to update
    * @param game
    *   the field to save instead
    * @return
    *   Success if the game was updated successfully, Failure otherwise
    */
  def update( gameId: Option[Int], game: GameInterface ): Try[Unit]

  /** Delete game field from database.
    * @param gameId
    *   the game id to delete. If None, the last game is deleted
    * @return
    *   Success if the game was deleted successfully, Failure otherwise
    */
  def delete( gameId: Option[Int] ): Try[Unit]
