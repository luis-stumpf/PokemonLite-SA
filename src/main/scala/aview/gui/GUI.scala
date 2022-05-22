package de.htwg.se.pokelite
package aview.gui

import controller.Controller

import de.htwg.se.pokelite.HelloWorld.stage
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{ Button, Label }
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.GridPane
import scalafx.scene.shape.Rectangle

class GUI(GUIApp : GUIApp, val controller : Controller) extends JFXApp3 {
  override def start() : Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Charizard"
      scene = new Scene {
        root = new GridPane {
          val img = new Image( "https://img.pokemondb.net/sprites/black-white/anim/normal/charizard.gif", 200, 200, true, true )
          val imgView = new ImageView( img )
          val img2 = new Image( "https://img.pokemondb.net/sprites/black-white/anim/back-normal/blastoise.gif", 200, 200, true, true )
          val imgView2 = new ImageView( img2 )
          add( imgView, 1, 0 )
          add( imgView2, 0, 1 )
          val p1Status = new Label( "    Glurak HP: 150" )
          val p2Status = new Label( "    Turtok HP: 180" )
          add( p1Status, 0, 0 )
          add( p2Status, 1, 1 )
          val line1 = new Rectangle {
            height = 200
            width = 5
          }
          val line2 = new Rectangle {
            height = 200
            width = 5
          }
          add( line1, 3, 0 )
          add( line2, 3, 1 )
          val attack1 = new Button {
            text = "1: Attacke"
          }
          val attack2 = new Button {
            text = "2: Attacke"
          }
          val attack3 = new Button {
            text = "3: Attacke"
          }
          val attack4 = new Button {
            text = "4: Attacke"
          }
          add( attack1, 4, 0 )
          add( attack2, 4, 1 )
          add( attack3, 5, 0 )
          add( attack4, 5, 1 )
        }
      }
    }
  }
}
