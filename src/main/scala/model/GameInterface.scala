package de.htwg.se.pokelite
package model

import model.State
import model.PokemonArt
import model.FieldInterface
import model.PokemonType.{Bisaflor, Brutalanda, Glurak, Simsala, Turtok}
import model.impl.field.Field
import model.PokePlayerInterface

import scala.util.{Failure, Success, Try}
import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer

import scala.util.{Failure, Success}
import scala.xml.Node

trait GameRules {
  def maxPokePackSize: Int
  def maxPlayerNameLength: Int
  def calculateDamageMultiplicator(pokemonArt1 : PokemonArt, pokemonArt2 : PokemonArt) : Double
  def isIngame(state: State):Boolean
  def fromXML(node: Node):GameInterface
}

trait GameInterface {

  def state:State
  def player1: Option[PokePlayer]
  def player2: Option[PokePlayer]
  def winner: Option[PokePlayer]
  def turn: Int
  
  def toXML:Node

  def setStateTo(newState : State) : GameInterface

  def hasWinner: Boolean
  def addPlayerWith(name : String) : Try[GameInterface]
  
  def removePlayer(): GameInterface

  def interpretPokemonSelectionFrom(string : String) : Try[GameInterface]
  
  def removePokemonFromPlayer(): GameInterface

  def setNextTurn() : GameInterface

  def interpretAttackSelectionFrom(input : String) : Try[GameInterface]

  def reverseAttackWith(i : String) : GameInterface

  def selectPokemonFrom(input : String) : Try[GameInterface]

  override def toString : String

}
      

          

