package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.ControllerInterface
import scalafx.scene.control.Button
import scalafx.scene.Node
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.VBox
import scalafx.scene.image.ImageView

class SwitchPokemonPane(controller : ControllerInterface) extends VBox {
  spacing = 30
  def getPokemons : List[ Button ] = {
    if ( controller.game.turn == 1 )
      controller.game.player1.get.pokemons.contents.map[ Button ](p => new Button() {
        minWidth = 200
        minHeight = 60
        graphic = getPokeImage(p.get.pType.name)
        text = p.get.toString
        onAction = _ => controller.selectPokemon((controller.game.player1.get.pokemons.contents.indexOf(p)+1).toString)
      } )
    else
      controller.game.player2.get.pokemons.contents.map[ Button ](p => new Button() {
        minWidth = 200
        minHeight = 60
        graphic = getPokeImage(p.get.pType.name)
        text = p.get.toString
        onAction = _ => controller.selectPokemon((controller.game.player2.get.pokemons.contents.indexOf(p)+1).toString)
      } )

  }

  children = getPokemons


  def getPokeImage(input: String): ImageView =

    val pokeImg: Image = new Image("/pokemons/"+ input + "Front.gif", 50, 50, true, true)
    val imgView = new ImageView( pokeImg )
    imgView


}