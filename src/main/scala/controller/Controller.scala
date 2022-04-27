package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, Pokemon}

case class Controller(var field: Field) extends Observable:
  override def toString = field.toString;
  def setPlayerNameTo(name: String) =
    field = field.setPlayerNameTo(name)
    notifyObservers
  def setPokemonTo(pokemon: Pokemon) =
    field = field.setPokemonTo(pokemon)
    notifyObservers

  def giveControlToNextPlayer() =
    field = field.setNextTurn()
    notifyObservers
