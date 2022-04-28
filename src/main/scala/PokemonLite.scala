package de.htwg.se.pokelite

import aview.TUI
import controller.Controller
import model.{Field, NoPokemon, PokePlayer}

import scala.io.StdIn.readLine


@main def run() : Unit =
  val field = Field(50, PokePlayer("",1, NoPokemon()),PokePlayer("",2,NoPokemon()))
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run()