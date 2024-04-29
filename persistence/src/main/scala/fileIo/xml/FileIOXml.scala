package fileIo.xml

import model.impl.game.Game
import fileIo.FileIOInterface
import model.GameInterface
import java.io.{ File, PrintWriter }
import scala.xml.{ Elem, Node, PrettyPrinter }
import play.api.libs.json.JsValue
import play.api.libs.json.Json

class FileIOXml extends FileIOInterface {
  override def load: GameInterface = {
    val xml = scala.xml.XML.loadFile( "game.xml" )
    Game.fromXML( xml )

  }

  override def save( game: GameInterface ): Unit = {
    val pw = new PrintWriter( new File( "game.xml" ) )
    val prettyPrinter = new PrettyPrinter( 120, 4 )
    val xml = prettyPrinter.format( game.toXML )
    pw.write( xml )
    pw.close()
  }

  override def save( gameJson: JsValue ): Unit = {
    val pw = new PrintWriter( new File( "game.xml" ) )

    val asGame = Game.fromJson( gameJson )

    val prettyPrinter = new PrettyPrinter( 120, 4 )
    val xml = prettyPrinter.format( asGame.toXML )
    pw.write( xml )
    pw.close()
  }

  override def update( game: GameInterface ): Unit = {
    save( game )
  }

  override def delete: Unit = {
    val file = new File( "game.xml" )
    file.delete()
  }

}
