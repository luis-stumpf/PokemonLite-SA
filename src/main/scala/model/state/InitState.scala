package de.htwg.se.pokelite
package model.state

import util._

case class InitState() extends State{
  
  override def initPlayers():Option[Command] = Some(
    ChangeStateCommand( this, InitPlayerState() )
  )

}
