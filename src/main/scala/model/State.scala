package de.htwg.se.pokelite
package model

import util.{ Event, PreEvent, Command }

abstract class State {
  
  def initState() : Option[ Command ] = None
  def initPlayers() : Option[ Command ] = None

}

