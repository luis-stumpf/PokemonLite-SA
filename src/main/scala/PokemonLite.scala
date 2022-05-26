package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.{ Field, Game, PokePlayer }

import scala.io.StdIn.readLine
import aview.gui.GUI

object PokemonLite {
  val controller = Controller()
  val tui = TUI( controller )
  val gui = new GUI(controller)
  val guiTread = new Thread(() => {
    gui.main(Array.empty)
    System.exit(0)
  })
  guiTread.setDaemon(true)
  guiTread.start()


  def main(args:Array[String]): Unit = {
    var input = ""
    while (input != "quit")
      input = readLine()
      tui.processInputLine(input)
  }
}




