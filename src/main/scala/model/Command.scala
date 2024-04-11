package de.htwg.se.pokelite
package model

import scala.util.Try

trait Command[T]:

  def doStep( game: T ): Try[T]

  def undoStep( game: T ): Try[T]
