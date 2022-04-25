package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, Pokemon}

case class Controller(var field: Field) extends Observable:
  override def toString = field.toString;
  def setNameP1(name: String) =
    field = field.setNameP1(name)
    notifyObservers
  def setNameP2(name: String) =
    field = field.setNameP2(name = name)
    notifyObservers
  def setPokemonP1(pokemon: Pokemon) =
    field = field.setPokemonP1(pokemon = pokemon)
    notifyObservers
  def setPokemonP2(pokemon: Pokemon) =
    field = field.setPokemonP2(pokemon = pokemon)
    notifyObservers
