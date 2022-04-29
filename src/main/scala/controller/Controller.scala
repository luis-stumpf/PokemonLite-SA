package de.htwg.se.pokelite
package controller

import util.Observable
import model.{Field, Move, PokemonType}

case class Controller(var field: Field) extends Observable:
  override def toString: String = field.toString
  def doAndPublish(doThis: Move => Field, move: Move) =
    field = doThis(move)
    notifyObservers
  def setPlayerNameTo(move: Move) =
    field.setPlayerNameTo(move.name)
  def setPokemonTo(move: Move) =
    field.setPokemonTo(move.pokemon)
  def giveControlToNextPlayer(move: Move) =
    field.setNextTurn()
