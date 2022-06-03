package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.ControllerInterface
import scalafx.geometry.Insets
import scalafx.scene.control.{ Button, Label, TextField }
import scalafx.scene.layout.VBox
case class InitPane(controller: ControllerInterface) extends VBox {


  margin = Insets( 200, 500, 100, 200 )
  spacing = 10
  val label = new Label("PokemonLite")

  children = List(
    label,
    new Button("Lets Go!"){
      onAction = _ => controller.initPlayers()
    }
  )


}
