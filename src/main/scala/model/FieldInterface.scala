package de.htwg.se.pokelite
package model

import model.{ GameInterface, PokePlayerInterface, Pokemon }

trait FieldInterface:
  override def toString: String

  def mesh(): String

  def calcSpace( start: Double ): Int
