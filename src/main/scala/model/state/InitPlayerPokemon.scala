package de.htwg.se.pokelite
package model.state

import model.{Command, State}

case class InitPlayerPokemon() extends State{

  override def addPlayer(name:String ):Option[Command] = Some(
    AddPokemonCommand(name, this )
  )

  override def setInitBeginnerState():Option[Command] = Some(
    SetInitBeginnerStateCommand( this )
  )
}
