package de.htwg.se.pokelite
package model.impl.fileIo.json

import model.{FileIOInterface, GameInterface}
import model.impl.game.Game

import play.api.libs.json.Json

import java.io.*
import scala.io.Source
import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface {

  override def load: GameInterface = {
    val source = Source.fromFile("game.json")
    val json = Json.parse( source.getLines().mkString )
    source.close()
   Game.fromJson((json \ "game").get)
  }


  override def save(game: GameInterface): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    val save = Json.obj(
      "game" -> Json.toJson(game.toJson)
    )
    pw.write(Json.prettyPrint(save))
    pw.close()
  }

}
