package de.htwg.se.pokelite
package aview.gui

import controller.Controller
import model.State
import model.states.*

import de.htwg.se.pokelite.model.State
import de.htwg.se.pokelite.util.Observer
import scalafx.scene.layout.VBox
import scalafx.scene.Node
import scalafx.scene.layout.BorderPane
import scalafx.application.JFXApp3
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.control.{ Button, Label }
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.GridPane
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.{ Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize }
import scalafx.geometry.Side
import scalafx.scene.text.Text
import scalafx.geometry.Insets


class GUI( val controller : Controller) extends JFXApp3 with Observer {

  controller.add(this)

  override def update : Unit =
    fieldPane.children = List(text1,text2)

  var text1 = new Text(controller.game.player1.map(_.toString).getOrElse(""))
  var text2 = new Text("Turtok HP: 200")
  var fieldPane: VBox = new VBox() {
    padding = Insets(10, 10, 10, 100)

    /*val glurakImg: Image = new Image("/pokemons/GlurakFront.gif", 250, 250, true, true)
    val turtokImg: Image = new Image("/pokemons/TurtokBack.gif", 250, 250, true, true)
    val imgView = new ImageView( glurakImg )
    val imgView2 = new ImageView( turtokImg )
    add( imgView, 2, 0 )
    */
    children = List(text1, text2)
    /*add( imgView2, 0, 1 )*/

  }


  override def start() : Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "PokemonLite"
      scene = new Scene( 1600, 480 ) {
        root = new BorderPane {
          left = fieldPane
          val menu = false
          right = new InitPane(controller)
        }
      }
    }
  }
}






















/*
def setInitPlayerPane() : Unit = {
  val fieldPane : FieldPane = new FieldPane( controller.game )
  val nameInputPane : NameInputPane = new NameInputPane( controller )
  stage = new JFXApp3.PrimaryStage {
    title = "PokemonLite"
    scene = new Scene( 1600, 480 ) {
      root = new BorderPane {
        background = battleBackground
        left = fieldPane
        val menu = false
        right = nameInputPane

      }
    }
  }
}
*/
