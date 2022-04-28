package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, PokemonType}

case class Controller(var field: Field) extends Observable:
  override def toString = field.toString;
  def setPlayerNameTo(name: String) =
    field = field.setPlayerNameTo(name)
    notifyObservers
  def setPokemonTo(pokemon: PokemonType) =
    field = field.setPokemonTo(pokemon)
    notifyObservers

  def giveControlToNextPlayer() =
    field = field.setNextTurn()
    notifyObservers
