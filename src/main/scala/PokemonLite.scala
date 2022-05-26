package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.{ Field, Game, PokePlayer }

import scala.io.StdIn.readLine
import aview.gui.GUIApp


@main
def run() : Unit =
  val controller = Controller()

  val tui = TUI( controller )
  //tui.start
  GUIApp(controller)




