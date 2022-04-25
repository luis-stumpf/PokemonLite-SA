package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.Field

import scala.io.StdIn.readLine


@main def run() : Unit =
  val field = Field(3, "", "")
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run