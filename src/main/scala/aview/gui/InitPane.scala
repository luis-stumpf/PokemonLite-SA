package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.impl.Controller
import scalafx.geometry.Pos
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.scene.text.Font
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.VBox
case class InitPane(controller: Controller) extends VBox {

  spacing = 30
  alignment = Pos.Center

  val logo = new Image("/Logo.png",300,300,true,true)
  val logoView = new ImageView(logo)

  children = List(
    logoView,
    new Button("Lets Go!"){
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.initPlayers()
    }
  )


}