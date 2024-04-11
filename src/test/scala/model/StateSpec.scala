package de.htwg.se.pokelite
package model

import model.GameInterface
import model.commands.{ AddPlayerCommand, ChangeStateCommand }
import model.impl.game.Game
import model.GameInterface
import model.State.*

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

class StateSpec extends AnyWordSpec {

  "A State" when {
    "created" should {
      val state = FightingState

      "be in FightingState" in {
        state should be( FightingState )
      }

      "have a String representation" in {
        state.toString should be( "FightingState" )
      }

      "have a JSON representation" in {
        state.toJson.toString should be( "{\"stateVal\":\"FightingState\"}" )
      }

      "have a XML representation" in {
        val xml = <state>FightingState</state>
        state.toXML.toString should be( xml.toString )
      }

      "have a XML fromXML method" in {
        val xml = <state>FightingState</state>
        State.fromXML( xml ) should be( FightingState )
      }

      "have a XML fromXML method with InitPlayerState" in {
        val xml = <state>InitPlayerState</state>
        State.fromXML( xml ) should be( InitPlayerPokemonState )
      }

      "have a XML fromXML method with InitPlayerPokemonState" in {
        val xml = <state>InitPlayerPokemonState</state>
        State.fromXML( xml ) should be( InitPlayerPokemonState )
      }

      "have a XML fromXML method with SwitchPokemonState" in {
        val xml = <state>SwitchPokemonState</state>
        State.fromXML( xml ) should be( SwitchPokemonState )
      }

      "have a XML fromXML method with GameOverState" in {
        val xml = <state>GameOverState</state>
        State.fromXML( xml ) should be( GameOverState )
      }

      "have a JSON fromJson method" in {
        val json = "{\"stateVal\":\"FightingState\"}"

        State.fromJson( Json.parse( json ) ) should be( FightingState )
      }

      "have a JSON fromJson method with InitPlayerState" in {
        val json = "{\"stateVal\":\"InitPlayerState\"}"

        State.fromJson( Json.parse( json ) ) should be( InitPlayerPokemonState )
      }

      "have a JSON fromJson method with InitPlayerPokemonState" in {
        val json = "{\"stateVal\":\"InitPlayerPokemonState\"}"

        State.fromJson( Json.parse( json ) ) should be( InitPlayerPokemonState )
      }

      "have a JSON fromJson method with SwitchPokemonState" in {
        val json = "{\"stateVal\":\"SwitchPokemonState\"}"

        State.fromJson( Json.parse( json ) ) should be( SwitchPokemonState )
      }

      "have a JSON fromJson method with GameOverState" in {
        val json = "{\"stateVal\":\"GameOverState\"}"

        State.fromJson( Json.parse( json ) ) should be( GameOverState )
      }

    }
  }
}
