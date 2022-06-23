package de.htwg.se.pokelite
package model.impl.fileIo.json

import model.{FileIOInterface, GameInterface}

import play.api.libs.json.Json

import java.io.*
import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface {

  override def load: GameInterface = ???


  override def save(game: GameInterface): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(game.toJson))
    pw.close()
  }

}
