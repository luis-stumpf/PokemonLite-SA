package de.htwg.se.pokelite

import aview.TUI
import model.{FieldInterface, GameInterface, PokePlayerInterface}
import controller.impl
import controller.impl.Controller

import scala.io.StdIn.readLine
import aview.gui.GUI

import com.google.inject.{Guice, Injector}
import scalafx.application.Platform

import javax.inject.Inject

object PokemonLite {
  def main(args : Array[ String ]) : Unit = {
    val inject:Injector = Guice.createInjector(new PokemonLiteModule)
    val controller = inject.getInstance(classOf[Controller])
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




