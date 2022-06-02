package de.htwg.se.pokelite

import aview.TUI
import model.{FieldInterface, Game, PokePlayer}
import controller.impl
import controller.impl.Controller

import scala.io.StdIn.readLine
import aview.gui.GUI

import scalafx.application.Platform

object PokemonLite {
  def main(args : Array[ String ]) : Unit = {
    val controller = impl.Controller()
    val gui = GUI( controller )
    val guiTread = new Thread( () => {
      gui.main( Array.empty )
      System.exit( 0 )
    } )
    guiTread.setDaemon( true )
    guiTread.start()


    val tui = TUI( controller )
    var input = ""
    while ( input != "quit" )
      input = readLine()
      Platform.runLater(tui.processInputLine( input ))
  }
}




