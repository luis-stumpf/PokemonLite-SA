package de.htwg.se.pokelite
package model.commands

import model.states.*
import model.*

import scala.util.{Failure, Success, Try}

case class AddPokemonCommand(input: String, state: InitPlayerPokemonState) extends Command {

  override def doStep(game: GameInterface): Try[GameInterface] = game.interpretPokemonSelectionFrom(input)

  override def undoStep(game: GameInterface): GameInterface = game.removePokemonFromPlayer()
}
