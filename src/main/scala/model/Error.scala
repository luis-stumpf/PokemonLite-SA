package de.htwg.se.pokelite
package model

trait Error extends Throwable

case object NoPlayerName extends Error

case object NothingToUndo extends Error :
  println( "Error: There is nothing to undo." )

case object NothingToRedo extends Error :
  println( "Error: There is nothing to redo." )

case object NoCommandFound extends Error

case object NoInput extends Error

case object NoPokemonSelected extends Error

case object NoAttackSelected extends Error

case class WrongInput(input : String) extends Error :
  println( "Error: The Input \"" + input + "\" is not valid." )

case object NoDesicionMade extends Error

case object NoPlayerExists extends Error

case object NoPlayerToRemove extends Error

case class NameTooLong(input : String) extends Error :
  println( "Error: The Name \"" + input + "\" is too long." )

case object HorriblePlayerNameError extends Error:
  println("For some reason Player 2 has a name but Player 1 doesnt. Everything is F****d!")