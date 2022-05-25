package de.htwg.se.pokelite
package model.state

import model.{Command, State}

case class InitPlayerState() extends State{

  override def addPlayer(name:String ):Option[Command] = Some(
    AddPlayerCommand(name, this )
  )

  override def setInitBeginnerState():Option[Command] = Some(
    SetInitBeginnerStateCommand( this )
  )
}
