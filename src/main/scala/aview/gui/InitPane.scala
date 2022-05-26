package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.Controller
import scalafx.scene.control.{ Button, Label, TextField }
import scalafx.scene.layout.VBox
case class InitPane(controller: Controller) extends VBox {

  val label = new Label("PokemonLite")

  val textField:TextField = new TextField(){
    maxWidth = 200
    promptText = "Name"
  }

  children = List(
    label,
    new Button("Lets Go!"){
      onAction = _ => controller.initPlayers()
    },
    textField,
    new Button( "Add Player" ){
      onAction = _ => controller.addPlayer(textField.getText())
    }
  )


}
