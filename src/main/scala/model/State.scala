package de.htwg.se.pokelite
package model

import util.{ Event, PreEvent }

trait Stateable:
  var state : Option[ State ] = None
  def handle(e: Event): Option[ State ]

trait State:
  def toString: String


case class PreState(field: Field) extends State:
  override def toString = "Pregame\n"

case class MidState(field: Field) extends State:
  override def toString = "MidGame!\n"

case class EndState(field: Field) extends State:
  override def toString = "EndGame!\n"
