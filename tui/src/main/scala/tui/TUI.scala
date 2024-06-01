package tui

import controller.ControllerInterface
import model.State.*
import util.*

import scala.io.StdIn.readLine
import scala.util.{ Failure, Success, Try }

class TUI( using controller: ControllerInterface ) extends Observer:

  controller.add( this )

  def processInputLine( input: String ): Unit = {
    if input.isEmpty then update( "Error: No input detected." )
    else if input.charAt( 0 ) == 'y' then
      controller.doAndPublish( controller.undoMove )
    else if input.charAt( 0 ) == 'z' then
      controller.doAndPublish( controller.redoMove )
    else if input == "save" then controller.doAndPublish( controller.save )
    else if input == "load" then controller.doAndPublish( controller.load )
    else if input == "get" then controller.doAndPublish( controller.getGame )
    else if input == "update" then
      controller.doAndPublish( controller.updateGame )
    else if input == "delete" then
      controller.doAndPublish( controller.deleteGame )
    else
      controller.game.state match
        case InitState =>
          controller.doAndPublish( controller.initPlayers )
        case InitPlayerState =>
          controller.doAndPublish( controller.addPlayer, input )
        case InitPlayerPokemonState =>
          controller.doAndPublish( controller.addPokemons, input )
        case DesicionState =>
          controller.doAndPublish( controller.nextMove, input )
        case FightingState =>
          controller.doAndPublish( controller.attackWith, input )
        case SwitchPokemonState =>
          controller.doAndPublish( controller.selectPokemon, input )
        case GameOverState =>
          controller.doAndPublish( controller.restartTheGame )

  }

  override def update( message: String ): Unit = {
    println(
      if message == "success" then
        MatrixField( width = 120, height = 15, controller.game )
      else message
    )

    controller.game.state match
      case InitState              => println( welcomeStatement )
      case InitPlayerState        => println( nameInputRequest )
      case InitPlayerPokemonState => println( availablePokemon )
      case DesicionState          => println( selectionRequest )
      case FightingState          => println( availableAttackOptions )
      case SwitchPokemonState     => println( thePlayersPokemon )
      case GameOverState          => println( aGameOverMessage )
  }

  def welcomeStatement: String =
    "PokemonLite has loaded, type anything to begin."

  def nameInputRequest: String =
    "Enter name of Player " + controller.game.turn + ": "

  def getCurrentPlayerName: String =
    if controller.game.turn == 1 then
      controller.game.player1.map( _.name ).getOrElse( "" )
    else controller.game.player2.map( _.name ).getOrElse( "" )

  def availablePokemon: String =
    "Choose your Pokemon " + getCurrentPlayerName + ": \n" +
      "1: Glurak\n" +
      "2: Simsala\n" +
      "3: Brutalanda\n" +
      "4: Bisaflor\n" +
      "5: Turtok\n"

  def getCurrentPlayerPokemon: String =
    if controller.game.turn == 1 then getPlayer1Pokemon else getPlayer2Pokemon

  def getPlayer1Pokemon: String = controller.game.player1.get.pokemons.contents
    .map( p => p.get )
    .mkString( "   " )

  def getPlayer2Pokemon: String = controller.game.player2.get.pokemons.contents
    .map( p => p.get )
    .mkString( "   " )

  def thePlayersPokemon: String =
    "Your current Pokemon are: " + getCurrentPlayerPokemon

  def selectionRequest: String =
    "These are all possible decisions: 1: Attack, 2: Switch Pokemon"

  def availableAttackOptions: String = "Your possible Attacks are: 1, 2, 3, 4"

  def aGameOverMessage: String =
    "GameOver, " + controller.game.winner.get.name + " has won the Game!\n" +
      "Type anything to play again."
