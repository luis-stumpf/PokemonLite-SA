package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.{Field, Pokemon, NoPokemon}

import scala.io.StdIn.readLine


@main def run() : Unit =
  val field = Field(30, "", "", NoPokemon(), NoPokemon())
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run