package aview.gui

import controller.ControllerInterface
import controller.impl.Controller

import scalafx.geometry.{ Insets, Pos }
import scalafx.scene.control.{ Button, Label, TextField }
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.VBox
import scalafx.scene.text.Font

case class InitPane( controller: ControllerInterface ) extends VBox {

  spacing = 30
  alignment = Pos.Center

  val logo = new Image( "/Logo.png", 300, 300, true, true )
  val logoView = new ImageView( logo )

  children = List(
    logoView,
    new Button( "Lets Go!" ) {
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.doAndPublish( controller.initPlayers )
    }
  )

}
