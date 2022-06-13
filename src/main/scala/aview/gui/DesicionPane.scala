package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.impl.Controller
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox
import scalafx.scene.control.Button


case class DesicionPane(controller: Controller) extends HBox {
  spacing = 50
  children = List(
    new Button("Fight"){
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.nextMove("1")
    },
    new Button("Switch Pokemon"){
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.nextMove("2")
    },
  )
}