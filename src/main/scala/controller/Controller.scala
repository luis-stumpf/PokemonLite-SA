package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, PokemonType}

case class Controller(var field: Field) extends Observable:
  override def toString: String = field.toString
  def setPlayerNameTo(name: String): Unit =
    field = field.setPlayerNameTo(name)
    notifyObservers
  def setPokemonTo(pokemon: PokemonType): Unit =
    field = field.setPokemonTo(pokemon)
    notifyObservers

  def giveControlToNextPlayer(): Unit =
    field = field.setNextTurn()
    notifyObservers
