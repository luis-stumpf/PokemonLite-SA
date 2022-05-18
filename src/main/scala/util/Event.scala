package de.htwg.se.pokelite
package util


trait Event
case class PreEvent() extends Event
case class P1Event() extends Event
case class P2Event() extends Event
case class EndEvent() extends Event


