package controller.commands

import util.{ Error, NoInput }
import model.GameInterface
import model.State.*

import scala.util.{ Failure, Success, Try }
import model.State
import util.Command

case class SwitchPokemonCommand( input: String, state: State )
    extends Command[GameInterface] {

  override def doStep( game: GameInterface ): Try[GameInterface] =
    game.selectPokemonFrom( input )

  override def undoStep( game: GameInterface ): Try[GameInterface] =
    val newGame = game.selectPokemonFrom( input )
    Success( newGame.get.setStateTo( state ) )

}