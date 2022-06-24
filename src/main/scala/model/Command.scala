package de.htwg.se.pokelite
package model

import scala.util.Try

trait Command:

  def doStep( game : GameInterface ) : Try[ GameInterface ]

  def undoStep( game : GameInterface ) : GameInterface
