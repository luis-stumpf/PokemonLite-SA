package de.htwg.se.pokelite
package aview.gui

import controller.impl.Controller
import model.State
import model.states.*

import de.htwg.se.pokelite.model.State
import de.htwg.se.pokelite.util.Observer
import scalafx.scene.layout.Priority
import scalafx.scene.layout.HBox
import scalafx.scene.layout.VBox
import scalafx.scene.Node
import scalafx.scene.layout.BorderPane
import scalafx.application.JFXApp3
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.scene.shape.Rectangle
import scalafx.scene.layout.{Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize}
import scalafx.geometry.Side
import scalafx.scene.text.Text
import scalafx.geometry.Insets


class GUI( val controller : Controller) extends JFXApp3 with Observer {

  controller.add(this)

  override def update(message:String) : Unit =
    fieldPane.children = new GridPane() {


      val glurakImg: Image = new Image("/pokemons/"+controller.game.player1.map(_.pokemons.contents.apply(controller.game.player1.get.currentPoke).map(_.pType.name).getOrElse("")).getOrElse("") + "Front.gif", 250, 250, true, true)
      val turtokImg: Image = new Image("/pokemons/"+controller.game.player2.map(_.pokemons.contents.apply(controller.game.player2.get.currentPoke).map(_.pType.name).getOrElse("")).getOrElse("") + "Back.gif", 250, 250, true, true)
      val imgView = new ImageView( glurakImg )
      val imgView2 = new ImageView( turtokImg )
      add( imgView, 2, 0 )
      add( imgView2, 0, 1 )
      val text1:Text = new Text(controller.game.player1.map(_.pokemons.contents.apply(controller.game.player1.get.currentPoke).map(_.toString).getOrElse("")).getOrElse(""))
      val text2:Text = new Text(controller.game.player2.map(_.pokemons.contents.apply(controller.game.player2.get.currentPoke).map(_.toString).getOrElse("")).getOrElse(""))
      add(text1, 1, 0)
      add(text2, 1, 1)
    }

    menuPane.children =
      controller.game.state match
        case InitState() => new InitPane(controller)
        case InitPlayerState() => new NameInputPane(controller)
        case InitPlayerPokemonState() => new PlayerPokemonPane(controller)
        case FightingState() => new FightingPane(controller)
        case DesicionState() => DesicionPane(controller)
        case SwitchPokemonState() => new SwitchPokemonPane(controller)

    topPane.children = new HBox() {
      hgrow = Priority.Always
      vgrow = Priority.Always
      padding = Insets(10)
      fillHeight = true
      var undo:Button = new Button("<-"){
        onAction = _ => controller.undoMove()
      }
      var redo:Button = new Button("->") {
        onAction = _ => controller.redoMove()
      }
      children = List(undo, redo)
    }

  var fieldPane: VBox = new VBox() {
    padding = Insets(10, 10, 10, 100)

  }

  var menuPane: VBox = new VBox() {

  }

  var topPane: HBox = new HBox() {

  }



  override def start() : Unit = {
    stage = new JFXApp3.PrimaryStage {

      val fieldBackground : Background = getBackground( "/backgroundField.png" )
      val menuBackground : Background = getBackground( "/backgroundMenu.png" )
      private def getBackground(url : String) : Background = {
        val tile : Image = new Image( url )
        val backgroundPosition = new BackgroundPosition(Side.Left, 0, false, Side.Top, 0, false)
        val backgroundImage = new BackgroundImage( tile, BackgroundRepeat.NoRepeat, BackgroundRepeat.NoRepeat, backgroundPosition,
          new BackgroundSize( 800, 480, false, false, false, false ) )
        new Background( new javafx.scene.layout.Background( backgroundImage ) )
      }
      title = "PokemonLite"
      scene = new Scene() {
        root = new BorderPane {
          left = new HBox {
            background = fieldBackground
            children = fieldPane
          }
          right = new HBox {
            background = menuBackground
            children = menuPane
          }
          bottom = topPane
        }
      }
      update("success")
    }
  }
}



















