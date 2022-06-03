package de.htwg.se.pokelite
package model

import model.State
import model.PokemonArt
import model.FieldInterface
import model.PokemonType.{ Bisaflor, Brutalanda, Glurak, Simsala, Turtok }
import model.impl.field.Field
import model.PokePlayerInterface

import de.htwg.se.pokelite.model.impl.pokePlayer.PokePlayer

import scala.util.{ Failure, Success }



trait GameInterface {

  def state:State
  def player1: Option[PokePlayer]
  def player2: Option[PokePlayer]
  def winner: Option[PokePlayer]
  def turn: Int

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
      

          

