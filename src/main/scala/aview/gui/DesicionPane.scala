package aview.gui

import controller.ControllerInterface
import controller.impl.Controller

import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

case class DesicionPane( controller: ControllerInterface ) extends HBox {
  spacing = 50
  children = List(
    new Button( "Fight" ) {
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.doAndPublish( controller.nextMove, "1" )
    },
    new Button( "Switch Pokemon" ) {
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.doAndPublish( controller.nextMove, "2" )
    }
  )
}
