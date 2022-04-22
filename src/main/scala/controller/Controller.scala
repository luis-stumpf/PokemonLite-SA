package de.htwg.se.pokelite
package controller

import util.Observable
import model.Field

case class Controller(var field: Field) extends Observable:
  override def toString = field.toString;