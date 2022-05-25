package de.htwg.se.pokelite
package model

import scala.util.Try

trait Command:

  def doStep(game : Game) : Try[ Game ]

  def undoStep(game : Game) : Game
