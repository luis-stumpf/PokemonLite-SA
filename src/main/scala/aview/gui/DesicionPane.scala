package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.Controller
import scalafx.scene.layout.HBox
import scalafx.scene.control.Button


case class DesicionPane(controller: Controller) extends HBox {
  children = List(
    new Button("Fight"){
      onAction = _ => controller.nextMove("1")
    },
    new Button("Switch Pokemon"){
      onAction = _ => controller.nextMove("2")
    },
  )
}
