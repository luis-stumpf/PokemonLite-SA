package de.htwg.se.pokelite
package model

import model.{ GameInterface, PokePlayerInterface, Pokemon }

trait FieldInterface:
  override def toString : String

  def mesh( height : Int ) : String

  def row( ) : String

  def col( height : Int ) : String

  def printPlayer1Stats( ) : String

  def printPlayer2Stats( ) : String

  def calcSpace( start : Double, element : String ) : Int

  def calcSpace( start : Double ) : Int

  def cleanSite( ) : String

  def printTopPlayer( ) : String

  def printTopPokemon( ) : String

  def printBottomPlayer( ) : String

  def printBottomPokemon( ) : String

  def printTopAttacks( ) : String

  def printBottomAttacks( ) : String

  def printTopAttacksOf( pokemon : Option[ Pokemon ] ) : String

  def printBottomAttacksOf( pokemon : Option[ Pokemon ] ) : String


