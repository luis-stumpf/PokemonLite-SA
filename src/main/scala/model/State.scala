package de.htwg.se.pokelite
package model

import util.{ Event, PreEvent }

trait Stateable:
  var state : Option[ State ] = None
  def handle(e: Event): Option[ State ]

trait State:
  def toString: String


case class PreState(field: Field) extends State:
  override def toString = "Pre game\n"

case class P1State(field: Field) extends State:
  override def toString = field.player1.name + " ist dran!\n"

case class P2State(field: Field) extends State:
  override def toString = field.player2.name + " ist dran!\n"
