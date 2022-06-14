package de.htwg.se.pokelite
package aview.gui

import de.htwg.se.pokelite.controller.impl.Controller
import scalafx.scene.text.Font
import scalafx.scene.control.Label
import scalafx.geometry.Pos
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.scene.layout.VBox
import scalafx.scene.control.Button

case class GameOverPane(controller: Controller) extends VBox {
  spacing = 30
  alignment = Pos.Center

  val text = new Label("GAMEOVER!\nThe Winner is: " + controller.game.winner.get){
    font = Font.font(20)
  }

  val logo = new Image("/Logo.png", 300, 300, true, true)
  val logoView = new ImageView(logo)

  children = List(

    logoView,
    text,
    new Button("Play again!"){
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.restartTheGame()
    }
  )
}
    
