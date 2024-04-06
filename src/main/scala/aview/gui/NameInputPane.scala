package de.htwg.se.pokelite
package aview.gui

import controller.ControllerInterface
import controller.impl.Controller

import scalafx.geometry.Insets
import scalafx.scene.control.{ Button, TextField }
import scalafx.scene.layout.VBox
import scalafx.scene.text.Font

case class NameInputPane( controller: ControllerInterface ) extends VBox {

  spacing = 30
  val textField: TextField = new TextField() {
    minWidth = 200
    minHeight = 40
    promptText = "Name"
    font = Font.font( 15 )
  }
  children = List(
    textField,
    new Button( "Add Player" + getCurrentPlayerNumber() ) {
      minWidth = 200
      minHeight = 60
      onAction = _ =>
        controller.doAndPublish( controller.addPlayer( textField.getText() ) )
    }
  )

  def getCurrentPlayerNumber(): Int = controller.game.turn
}
