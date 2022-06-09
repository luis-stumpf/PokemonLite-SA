package de.htwg.se.pokelite
package model.commands

import model.{ Command, GameInterface, State }

import de.htwg.se.pokelite.model.impl.game.Game


import scala.util.Try

case class GameOverCommand(oldGame: GameInterface, state: State) extends Command {

  def doStep(game : GameInterface) : Try[ GameInterface ] = Try(Game())


  def undoStep(game : GameInterface) : GameInterface = oldGame

}
