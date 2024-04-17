package gui

import controller.ControllerInterface
import controller.impl.Controller

import scalafx.geometry.Pos
import scalafx.scene.control.{ Button, Label }
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.VBox
import scalafx.scene.text.Font

case class GameOverPane( controller: ControllerInterface ) extends VBox {
  spacing = 30
  alignment = Pos.Center

  val text = new Label(
    "GAMEOVER!\nThe Winner is: " + controller.game.winner.get
  ) {
    font = Font.font( 20 )
  }

  val logo = new Image( "/Logo.png", 300, 300, true, true )
  val logoView = new ImageView( logo )

  children = List(
    logoView,
    text,
    new Button( "Play again!" ) {
      minWidth = 200
      minHeight = 60
      onAction = _ => controller.doAndPublish( controller.restartTheGame )
    }
  )
}
