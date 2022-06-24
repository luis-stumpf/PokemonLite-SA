package de.htwg.se.pokelite
package model.states

import model.*
import model.commands.*
import util.*

case class InitState() extends State {

  override def initPlayers(): Option[Command] = Some(
    ChangeStateCommand(this, InitPlayerState())
  )

}
