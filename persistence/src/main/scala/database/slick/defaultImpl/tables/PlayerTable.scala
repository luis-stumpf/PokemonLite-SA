package database.slick.defaultImpl.tables

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class PlayerTable( tag: Tag )
    extends Table[( Int, String, String, Int )]( tag, "player" ) {
  def id = column[Int]( "id", O.PrimaryKey, O.AutoInc )
  def name = column[String]( "name" )
  def pokemons = column[String]( "pokemons" )
  def currentPoke = column[Int]( "current_poke" )

  def * = ( id, name, pokemons, currentPoke )

}
