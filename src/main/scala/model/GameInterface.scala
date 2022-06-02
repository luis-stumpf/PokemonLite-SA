package de.htwg.se.pokelite
package model

import model.State
import model.PokemonArt
import model.FieldInterface

import model.PokemonType.{ Glurak, Simsala, Brutalanda, Bisaflor, Turtok }
import model.impl.field.Field
import model.PokePlayerInterface

import scala.util.{ Failure, Success }



trait GameInterface {
  val gameState: State
  val gameTurn: Int
  val gamePlayer1: Option[PokePlayerInterface]
  val gamePlayer2: Option[PokePlayerInterface]
  val gameWinner: Option[PokePlayerInterface]

  def setStateTo(newState : State) : GameInterface


  def addPlayer(name : String) : GameInterface
  
  def removePlayer(): GameInterface

  def addPokemonToPlayer(input : String) : GameInterface
  
  def removePokemonFromPlayer(): GameInterface

  def setNextTurn() : GameInterface

  def attackWith(i : String) : GameInterface

  def reverseAttackWith(i : String) : GameInterface

  def selectPokemon(input : Int) : GameInterface

  override def toString : String

}
      

          

