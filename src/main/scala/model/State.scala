package de.htwg.se.pokelite
package model

import util.{ Event, PreEvent, Command }

abstract class State {
  
  def initPlayers() : Option[ Command ] = None
  def addPlayer(name: String): Option[Command] = None

}

