package service
import scala.io.StdIn.readLine
import tui.TUI
import tui.TUIKafka
import java.io.IOException
import di.ControllerModule
import di.ControllerRestModule

/*
object TuiService extends App:
  Starter( TUI( using ControllerModule.given_ControllerInterface ) ).start()

 */

object TuiRestService extends App:
  Starter( TUI( using ControllerRestModule.given_ControllerInterface ) )
    .start()

object TuiKafkaService extends App:
  StarterKafka( TUIKafka() )
    .start()

case class Starter( tui: TUI ):
  def start() = {
    println( "PokemonLite has loaded, type anything to begin." )
    while true do tui.processInputLine( readLine() )
  }

  def processInput(): Unit = {
    try {
      val input = readLine()
      if (input != "quit") {
        tui.processInputLine( input )
        processInput()
      }
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

case class StarterKafka( tui: TUIKafka ):
  def start() = {
    println( "PokemonLite has loaded, type anything to begin." )
    while true do tui.processInputLine( readLine() )
  }

  def processInput(): Unit = {
    try {
      val input = readLine()
      if (input != "quit") {
        tui.processInputLine( input )
        processInput()
      }
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }
