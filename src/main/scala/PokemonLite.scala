package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.{Field, PokePlayer}

import scala.io.StdIn.readLine


@main def run() : Unit =
  val field = Field(50, PokePlayer("",1), PokePlayer("",2))
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run()