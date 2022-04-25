package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, Pokemon}

case class Controller(var field: Field) extends Observable:
  override def toString = field.toString;
  def setNameP1(name: String) =
    field = field.setP1(name)
    notifyObservers
  def setNameP2(name: String) =
    field = field.setP2(name = name)
    notifyObservers
  def setPokemonP1(pokemon: Pokemon) =
    field = field.setP1P(pokemon = pokemon)
    notifyObservers
  def setPokemonP2(pokemon: Pokemon) =
    field = field.setP2P(pokemon = pokemon)
    notifyObservers
