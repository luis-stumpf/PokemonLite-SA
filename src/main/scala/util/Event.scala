package de.htwg.se.pokelite
package util


trait Event

case class PreEvent() extends Event

case class MidEvent() extends Event

case class EndEvent() extends Event


