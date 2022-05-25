package de.htwg.se.pokelite
package aview.gui

import controller.Controller
import aview.gui.GUIApp._

import scalafx.scene.layout.VBox
import scalafx.scene.Node
import scalafx.scene.layout.BorderPane
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{ Button, Label }
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.GridPane
import scalafx.scene.shape.Rectangle

class GUI(GUIApp : GUIApp, val controller : Controller) extends JFXApp3 {
  override def start() : Unit = {
    val fieldPane:FieldPane = new FieldPane
    val menuPane:MenuPane = new MenuPane
    val inputPane:InputPane = new InputPane
    stage = new JFXApp3.PrimaryStage {
      title = "PokemonLite"
      scene = new Scene(1600, 480) {
        root = new BorderPane {
          background = battleBackground
          left = fieldPane
          val menu = false
          right = if(menu == true) menuPane else inputPane




        }
      }
    }
  }
}
