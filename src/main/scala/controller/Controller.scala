package de.htwg.se.pokelite
package controller

import util.Observable
import model.Field

case class Controller(var field: Field) extends Observable:
  override def toString = field.toString;
  def setNameP1(name: String) =
    field.printP1left(name = name)
    notifyObservers
  def setNameP2(name: String) =
    field.printP2left(name = name)
    notifyObservers
  