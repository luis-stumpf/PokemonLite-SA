package database.slick.defaultImpl.tables

import slick.jdbc.PostgresProfile.api._

class TestTable( tag: Tag ) extends Table[( Int, String )]( tag, "test" ) {
  def id = column[Int]( "id", O.PrimaryKey, O.AutoInc )
  def name = column[String]( "name" )

  def * = ( id, name )
}
