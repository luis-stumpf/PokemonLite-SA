package de.htwg.se.pokelite

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane
import scalafx.scene.image.{ Image, ImageView }

object HelloWorld extends JFXApp3 {

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Charizard"
      scene = new Scene(1000, 500) {
        val img = new Image("https://img.pokemondb.net/sprites/black-white/anim/normal/charizard.gif", 500, 500, true, true)
        val imgView = new ImageView(img)
        val img2 = new Image("https://img.pokemondb.net/sprites/black-white/anim/back-normal/blastoise.gif", 500, 500, true, true)
        val imgView2 = new ImageView(img2)
        val border = new BorderPane
        border.left = imgView2
        border.right = imgView
        root = border
      }
    }
  }
}