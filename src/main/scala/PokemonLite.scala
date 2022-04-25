package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.{Field, Pokemon}

import scala.io.StdIn.readLine


@main def run() : Unit =
  val field = Field(3, "", "", Pokemon("No Pokemon", -1), Pokemon("No Pokemon", -1))
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run