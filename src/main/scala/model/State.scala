package de.htwg.se.pokelite
package model

import model.GameInterface

import de.htwg.se.pokelite.model.states.{DesicionState, FightingState, GameOverState, InitPlayerPokemonState, SwitchPokemonState}
import play.api.libs.json.{JsValue, Json}

import scala.xml.Node

object State {
  def fromXML(node:Node): State =
    val state = (node \\ "state").text match {
      case "DesicionState()" => DesicionState()
      case "FightingState()" => FightingState()
      case "GameOverState()" => GameOverState()
      case "InitPlayerPokemonState()" => InitPlayerPokemonState()
      case "InitPlayerState()" => InitPlayerPokemonState()
      case "SwitchPokemonState()" => SwitchPokemonState()
    }
    state

  def fromJson(json: JsValue): State =
    val state = (json \ "stateVal").get.toString().replace("\"", "") match {
      case "DesicionState()" => DesicionState()
      case "FightingState()" => FightingState()
      case "GameOverState()" => GameOverState()
      case "InitPlayerPokemonState()" => InitPlayerPokemonState()
      case "InitPlayerState()" => InitPlayerPokemonState()
      case "SwitchPokemonState()" => SwitchPokemonState()
    }
    state
}

trait State {

  def initPlayers() : Option[ Command ] = None
  def addPlayer(name: String): Option[Command] = None
  def addPokemons(name : String) : Option[ Command ] = None
  def attackWith(input: String) : Option[Command] = None
  def switchPokemon(input: String): Option[Command] = None
  def nextMove(input: String): Option[Command] = None
  def switchPokemonTo(input: String): Option[Command] = None
  def restartTheGame(game: GameInterface):Option[Command] = None
  def toJson: JsValue =
    Json.obj(
      "stateVal" -> Json.toJson(this.toString)
    )

}

