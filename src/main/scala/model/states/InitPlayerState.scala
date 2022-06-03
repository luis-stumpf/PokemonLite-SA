package de.htwg.se.pokelite
package model.states

import model.{Command, State}
import model.commands.*

case class InitPlayerState() extends State{

  override def addPlayer(name:String ):Option[Command] = Some(
    AddPlayerCommand(name, this )
  )
}
