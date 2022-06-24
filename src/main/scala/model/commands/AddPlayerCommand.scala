package de.htwg.se.pokelite
package model.commands

import model.impl.game.Game
import model.states.*
import model.*

import scala.util.{Failure, Success, Try}


case class AddPlayerCommand(name: String, state: InitPlayerState) extends Command {

  override def doStep(game: GameInterface): Try[GameInterface] = game.addPlayerWith(name)

  override def undoStep(game: GameInterface): GameInterface = game.removePlayer()

}
