package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.ControllerInterface
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox
import scalafx.scene.control.Button


case class DesicionPane(controller: ControllerInterface) extends HBox {
  spacing = 10
  margin = Insets( 200, 500, 100, 200 )

  children = List(
    new Button("Fight"){
      onAction = _ => controller.nextMove("1")
    },
    new Button("Switch Pokemon"){
      onAction = _ => controller.nextMove("2")
    },
  )
}
