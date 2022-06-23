package de.htwg.se.pokelite
package model.impl.fileIo.xml

import model.{FileIOInterface, GameInterface}
import model.impl.game.Game

import java.io.{File, PrintWriter}
import scala.xml.{Elem, Node, PrettyPrinter}


case class XMLParseError( expected:String, got:String ) extends RuntimeException {
  override def toString:String = "XMLParseError: Expected -> '" + expected + "', Got -> '" + got + "'"
}

class FileIO extends FileIOInterface{
  override def load: GameInterface = {
    val xml = scala.xml.XML.loadFile("game.xml")
    Game.fromXML(xml)

  }

  override def save(game: GameInterface): Unit = {
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(game.toXML)
    pw.write(xml)
    pw.close()
  }


  implicit class XMLNode( node:Node ) {
    def firstChild(): Option[Node] = {
      val r = node.child.collectFirst {
        case e: Elem => e
      }
      r
    }

    def childOf(tag: String): Node = {
      val child = (node \ tag).headOption
      if (child.isEmpty)
        throw XMLParseError(expected = tag, got = "Nothing")
      val c = child.get.firstChild()
      c match {
        case Some(c) => c
        case None => throw XMLParseError(expected = "Content in tag[" + tag + "]", got = "Nothing")
      }
    }

  }




}
