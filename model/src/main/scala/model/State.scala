package model

import model.GameInterface

import play.api.libs.json.{ JsValue, Json }

import scala.xml.Node

enum State {
  case InitState extends State
  case DesicionState extends State
  case FightingState extends State
  case GameOverState extends State
  case InitPlayerPokemonState extends State
  case InitPlayerState extends State
  case SwitchPokemonState extends State

  def toJson: JsValue =
    Json.obj( "stateVal" -> Json.toJson( this.toString ) )

  def toXML: Node =
    <state>{this.toString}</state>
}

object State {
  def fromXML( node: Node ): State =
    ( node \\ "state" ).text.replace( " ", "" ) match {
      case "DesicionState"          => DesicionState
      case "FightingState"          => FightingState
      case "GameOverState"          => GameOverState
      case "InitPlayerPokemonState" => InitPlayerPokemonState
      case "InitPlayerState"        => InitPlayerState
      case "SwitchPokemonState"     => SwitchPokemonState
      case "InitState"              => InitState
    }

  def fromJson( json: JsValue ): State =
    ( json \ "stateVal" ).get.toString().replace( "\"", "" ) match {
      case "DesicionState"          => DesicionState
      case "FightingState"          => FightingState
      case "GameOverState"          => GameOverState
      case "InitPlayerPokemonState" => InitPlayerPokemonState
      case "InitPlayerState"        => InitPlayerState
      case "SwitchPokemonState"     => SwitchPokemonState
      case "InitState"              => InitState
    }

  def fromString( state: String ): State =
    state match {
      case "DesicionState"          => DesicionState
      case "FightingState"          => FightingState
      case "GameOverState"          => GameOverState
      case "InitPlayerPokemonState" => InitPlayerPokemonState
      case "InitPlayerState"        => InitPlayerState
      case "SwitchPokemonState"     => SwitchPokemonState
      case "InitState"              => InitState
    }
}
