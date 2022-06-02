package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.ControllerInterface
import scalafx.scene.control.Button
import scalafx.scene.Node
import scalafx.scene.layout.VBox

class SwitchPokemonPane(controller : ControllerInterface) extends VBox {
  def getPokemons : List[ Button ] = {
    if ( controller.game.turn == 1 )
      controller.game.player1.get.getPokemons.contents.map[ Button ]( p => new Button() {
        text = p.get.toString
        onAction = _ => controller.selectPokemon((controller.game.player1.get.getPokemons.contents.indexOf(p)+1).toString)
      } )
    else
      controller.game.player2.get.getPokemons.contents.map[ Button ]( p => new Button() {
        text = p.get.toString
        onAction = _ => controller.selectPokemon((controller.game.player2.get.getPokemons.contents.indexOf(p)+1).toString)
      } )

  }

  children = getPokemons


}
