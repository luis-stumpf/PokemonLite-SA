package de.htwg.se.pokelite
package model.impl.fileIo.xml

import model.{FileIOInterface, GameInterface}

import java.io.{File, PrintWriter}
import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface{
  override def load: GameInterface = {
    var game: GameInterface = null
    val file = scala.xml.XML.loadFile("game.xml")
    val state = (file \\ "state").text.toString.trim()
    val player1 = (file \\ "player1").text.toString.trim()
    val player2 = (file \\ "player2").text.toString.trim()
    val turn = (file \\ "turn").text.toString.trim()
    val winner = (file \\ "winner").text.toString.trim()
    game

  }

  override def save(game: GameInterface): Unit = {
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(game.toXML)
    pw.write(xml)
    pw.close()
  }


}
