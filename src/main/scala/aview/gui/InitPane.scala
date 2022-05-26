package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.Controller
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.scene.control.Button
case class InitPane(controller: Controller) extends VBox {

  val label = new Label("PokemonLite")
  children = List(
    label,
    new Button("Lets Go!"){
      onAction = _ => controller.initPlayers()
    }
  )

}
