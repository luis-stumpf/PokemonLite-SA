package de.htwg.se.pokelite
package model.commands

import model.states.{ DesicionState, FightingState, SwitchPokemonState }
import model.{ Command, Error, GameInterface, NoInput }

import scala.util.{ Failure, Success, Try }

case class SwitchPokemonCommand( input : String, state : SwitchPokemonState ) extends Command {

  override def doStep( game : GameInterface ) : Try[ GameInterface ] = game.selectPokemonFrom( input )


  override def undoStep( game : GameInterface ) : GameInterface =
    val newGame = game.selectPokemonFrom( input )
    newGame.get.setStateTo( state )

}