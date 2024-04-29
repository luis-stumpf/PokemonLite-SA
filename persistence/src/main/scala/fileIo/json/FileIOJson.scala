package fileIo.json

import model.impl.game.Game
import fileIo.FileIOInterface
import model.GameInterface

import play.api.libs.json.Json

import java.io.*
import scala.io.Source
import scala.xml.PrettyPrinter
import play.api.libs.json.JsValue
import model.State
import model.impl.pokePlayer.PokePlayer
import scala.util.Success
import scala.util.Failure
import util.CouldNotLoadGame
import scala.util.Try

class FileIOJson extends FileIOInterface {

  override def load: GameInterface = {
    val source = Source.fromFile( "game.json" )
    val json = Json.parse( source.getLines().mkString )
    source.close()
    Game.fromJson( ( json \ "game" ).get )
  }

  override def save( game: GameInterface ): Unit = {
    val pw = new PrintWriter( new File( "game.json" ) )
    val save = Json.obj( "game" -> Json.toJson( game.toJson ) )
    pw.write( Json.prettyPrint( save ) )
    pw.close()
  }

  override def save( gameJson: JsValue ): Unit = {
    val pw = new PrintWriter( new File( "game.json" ) )
    val save = Json.obj( "game" -> gameJson )
    pw.write( Json.prettyPrint( save ) )
    pw.close()
  }

  override def update( game: GameInterface ): Unit = {
    save( game )
  }

  override def delete: Unit = {
    val file = new File( "game.json" )
    file.delete()
  }

}
