package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.control.ComboBox
import model.PokemonType
import controller.impl.Controller

import de.htwg.se.pokelite.controller.ControllerInterface
import de.htwg.se.pokelite.model.impl.game.Game
import scalafx.scene.layout.GridPane
import scalafx.scene.control.Button
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox

case class PlayerPokemonPane(controller: ControllerInterface) extends GridPane {

  val confirm: Button = new Button("Confrim") {
    minWidth = 200
    minHeight = 40
    margin = Insets(20)
    onAction = _ => controller.addPokemons(list.map(p => getPokemons(p)).mkString(""))
  }

  var list = (1 to Game.maxPokePackSize).map[ComboBox[String]](p => new ComboBox[String](PokemonType.values.map("" + _.name)) {
    minWidth = 200
    minHeight = 40
    margin = Insets(20)
  })

  add(new VBox(){
    children = list
  },0 ,0)
  add(confirm, 1, 0)






  def getPokemons(comboBox: ComboBox[String]): String =
    comboBox.getValue.match
      case "Glurak" => "1"
      case "Simsala" => "2"
      case "Brutalanda" => "3"
      case "Bisaflor" => "4"
      case "Turtok" => "5"
      case _ => ""

}

