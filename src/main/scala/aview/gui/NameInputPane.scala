package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.control.Button
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox
import scalafx.scene.control.TextField
import controller.ControllerInterface

import scalafx.scene.text.Font

case class NameInputPane(controller: ControllerInterface) extends VBox {

  spacing = 30
  val textField:TextField = new TextField(){
    minWidth = 200
    minHeight = 40
    promptText = "Name"
    font = Font.font(15)
  }
  children = List(
    textField,
    new Button( "Add Player"+ getCurrentPlayerNumber() ){
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.addPlayer(textField.getText())
    } )
  def getCurrentPlayerNumber(): Int = controller.game.turn
}