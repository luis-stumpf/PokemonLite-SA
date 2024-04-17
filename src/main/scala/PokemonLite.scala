package main
import com.google.inject.{ Guice, Injector }
import scalafx.application.Platform

import javax.inject.Inject
import scala.io.StdIn.readLine
import java.io.IOException
import controller.ControllerInterface
import tui.TUI
import gui.GUI

object PokemonLite {
  val inject: Injector = Guice.createInjector( new PokemonLiteModule )
  val controller =
    inject.getInstance( classOf[ControllerInterface] )
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
