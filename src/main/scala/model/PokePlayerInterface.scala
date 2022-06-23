package de.htwg.se.pokelite
package model

import model.{GameInterface, Pokemon}
import model.PokePack

import play.api.libs.json.JsValue

import scala.xml.Node

trait PokePlayerInterface:
  def name: String
  def pokemons: PokePack
  def currentPoke: Int
  override def toString: String

  def setPokemonTo(newPokemons : PokePack) : PokePlayerInterface

  def setPokePlayerNameTo(newName : String) : PokePlayerInterface

  def setCurrentPokeTo(number: Int): PokePlayerInterface

  def checkForDefeat() : Boolean
  
  def toXML:Node
  
  def toJson:JsValue




