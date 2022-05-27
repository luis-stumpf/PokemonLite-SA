package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.control.Button
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox
import scalafx.scene.control.TextField
import controller.Controller

case class NameInputPane(controller: Controller) extends VBox {
  margin = Insets( 200, 500, 100, 200 )
  spacing = 10

  val textField:TextField = new TextField(){
    maxWidth = 200
    promptText = "Name"
  }
  children = List(
    textField,
    new Button( "Add Player"+ getCurrentPlayerNumber() ){
      onAction = _ => controller.addPlayer(textField.getText())
    } )
  def getCurrentPlayerNumber(): Int = controller.game.turn
}
