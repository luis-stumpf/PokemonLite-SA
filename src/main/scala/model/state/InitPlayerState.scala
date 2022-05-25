package de.htwg.se.pokelite
package model.state

import model.*

class InitPlayerState extends State{

  override def addPlayer(name:String ):Option[Command] = Some(
    AddPlayerCommand( playerColor, name, this )
  )

  override def setInitBeginnerState():Option[Command] = Some(
    SetInitBeginnerStateCommand( this )
  )
}
