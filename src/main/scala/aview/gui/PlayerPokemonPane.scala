package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.control.ComboBox
import model.PokemonType
import controller.Controller

import scalafx.scene.control.Button
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

case class PlayerPokemonPane(controller: Controller) extends HBox {
  spacing = 10
  margin = Insets( 200, 500, 100, 200 )

  val comboBox1 : ComboBox[ String ] = new ComboBox[ String ]( PokemonType.values.map( _.name ) ) {
    margin = Insets(20, 20, 20, 20)
  }
  val comboBox2 : ComboBox[ String ] = new ComboBox[ String ]( PokemonType.values.map( _.name ) ) {
    margin = Insets(20, 20, 20, 20)
  }
  val comboBox3 : ComboBox[ String ] = new ComboBox[ String ]( PokemonType.values.map( _.name ) ) {
    margin = Insets(20, 20, 20, 20)
  }
  val confirm:Button = new Button("Confrim"){
    onAction = _ => controller.addPokemons(""+ getPokemons(comboBox1) + getPokemons(comboBox2) + getPokemons(comboBox3))
  }
  children = List(comboBox1,comboBox2,comboBox3, confirm)


  def getPokemons(comboBox: ComboBox[String]):String =
    comboBox.getValue. match
      case "Glurak" => "1"
      case "Simsala" => "2"
      case "Brutalanda" => "3"
      case "Bisaflor" => "4"
      case "Turtok" => "5"
      case _ => ""

}

