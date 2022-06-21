package de.htwg.se.pokelite
package model.impl.fileIo.json

import model.{FileIOInterface, GameInterface}

import java.io._
import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface {

  override def load: GameInterface = {
    var game: GameInterface = null
    val file = scala.xml.XML.loadFile("game.xml")
    val state = (file \\ "state").text.toString.trim()
    val player1 = (file \\ "player1").text.toString.trim()
    val player2 = (file \\ "player2").text.toString.trim()
    val turn = (file \\ "turn").text.toString.trim()
    val winner = (file \\ "winner").text.toString.trim()

  }

  override def save(game: GameInterface): Unit = {
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameToXML(game))
    pw.write(xml)
    pw.close()
  }


  def gameToXML(game: GameInterface) =
    <game>
      <state>{game.state}</state>
      <player1>{game.player1.getOrElse("")}</player1>
      <player2>{game.player2.getOrElse("")}</player2>
      <turn>{game.turn}</turn>
      <winner>{game.winner.getOrElse("")}</winner>

    </game>

}
