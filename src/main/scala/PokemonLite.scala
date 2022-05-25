package de.htwg.se.pokelite

import aview.TUI
import aview.gui.GUIApp
import controller.Controller
import model.{ Field, Game, PokePlayer }

import scala.io.StdIn.readLine


@main
def run() : Unit =
  val field = Field( 50, PokePlayer( "", 1 ), PokePlayer( "", 2 ) )
  val controller = Controller(Game())
  val tui = TUI( controller )
  GUIApp(controller)
  tui.run()




