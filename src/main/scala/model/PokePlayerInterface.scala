package de.htwg.se.pokelite
package model

import model.{GameInterface, Pokemon}

import model.PokePack

trait PokePlayerInterface:
  def getName: String
  def getPokemons: PokePack[Option[Pokemon]]
  def getCurrentPoke: Int
  override def toString: String

  def setPokemonTo(newPokemons : PokePack[ Option[ Pokemon ] ]) : PokePlayerInterface

  def setPokePlayerNameTo(newName : String) : PokePlayerInterface

  def setCurrentPokeTo(number: Int): PokePlayerInterface

  def checkForDead() : Boolean




