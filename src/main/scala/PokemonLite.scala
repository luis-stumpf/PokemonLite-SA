package main
import scalafx.application.Platform

import scala.io.StdIn.readLine
import java.io.IOException
import controller.ControllerInterface
import tui.TUI
import gui.GUI
import di.PokemonLiteModule.given_ControllerInterface as Controller
import controller.ControllerRestApi
import persistence.PersistenceRestApi
import service.TuiService
import service.TuiRestService

object PokemonLite {
  val controller = Controller
  val controllerService = ControllerRestApi.run()
  val persistenceService = PersistenceRestApi.run()

  // val gui = GUI( controller )
  var input = ""

  def main( args: Array[String] ): Unit = {
    TuiRestService
    /*
    val guiTread = new Thread( () => {
      gui.main( Array.empty )
      System.exit( 0 )
    } )
    guiTread.setDaemon( true )
    guiTread.start()

    processInput()
     */
  }
  /*
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
   */
}
