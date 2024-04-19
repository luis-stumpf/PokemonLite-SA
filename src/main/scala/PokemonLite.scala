package main
import scalafx.application.Platform

import scala.io.StdIn.readLine
import java.io.IOException
import controller.ControllerInterface
import tui.TUI
import gui.GUI
import di.PokemonLiteModule.given_ControllerInterface as Controller

object PokemonLite {
  val controller = Controller
  val gui = GUI( controller )
  val tui = TUI( controller )
  var input = ""

  def main( args: Array[String] ): Unit = {
    val guiTread = new Thread( () => {
      gui.main( Array.empty )
      System.exit( 0 )
    } )
    guiTread.setDaemon( true )
    guiTread.start()

    processInput()
    /*
    while (input != "quit")
      input = readLine()
      tui.processInputLine( input )
     */
  }

  def processInput(): Unit = {
    try {
      val input = readLine()
      if (input != "quit") {
        Platform.runLater( tui.processInputLine( input ) )
        processInput()
      }
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }
}
