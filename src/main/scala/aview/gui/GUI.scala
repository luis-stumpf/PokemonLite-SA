package de.htwg.se.pokelite
package aview.gui

import controller.impl.Controller
import model.impl.game.Game
import model.State
import model.states.*

import de.htwg.se.pokelite.aview.gui.pieces.HealthBar
import de.htwg.se.pokelite.model.State
import de.htwg.se.pokelite.util.Observer
import scalafx.scene.text.FontWeight
import scalafx.scene.text.Font
import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.Media
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import scalafx.scene.layout.ColumnConstraints
import scalafx.scene.layout.RowConstraints
import scalafx.scene.layout.AnchorPane
import scalafx.scene.layout.FlowPane
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
import scalafx.geometry.Pos


class GUI(val controller: Controller) extends JFXApp3 with Observer {

  controller.add(this)

  override def update(message: String): Unit =


    if (message != "success")
      Platform.runLater {
        new Alert(AlertType.Warning) {
          initOwner(stage)
          headerText = message
        }.showAndWait()
      }


    fieldPane.children = new GridPane() {

      columnConstraints = List(
        new ColumnConstraints(300),
        new ColumnConstraints(200),
        new ColumnConstraints(300)
      )
      rowConstraints = List(
        new RowConstraints(225),
        new RowConstraints(225),
        new RowConstraints(30)
      )


      val player1PokeImg: Image = new Image("/pokemons/" + controller.game.player1.map(_.pokemons.contents.apply(controller.game.player1.get.currentPoke).map(_.pType.name).getOrElse("")).getOrElse("") + "Front.gif", 250, 250, true, true)
      val player2PokeImg: Image = new Image("/pokemons/" + controller.game.player2.map(_.pokemons.contents.apply(controller.game.player2.get.currentPoke).map(_.pType.name).getOrElse("")).getOrElse("") + "Back.gif", 250, 250, true, true)
      val player1PokeView = new ImageView(player1PokeImg)
      val player2PokeView = new ImageView(player2PokeImg)
      add(player1PokeView, 2, 0)
      add(player2PokeView, 0, 1)

      if(Game.isIngame(controller.game.state)) {
        add(HealthBar(controller, 1), 1, 0)
        add(HealthBar(controller, 2), 1, 1)
      }

    }

    menuPane.children =
      controller.game.state match
        case InitState() => new InitPane(controller)
        case InitPlayerState() => new NameInputPane(controller)
        case InitPlayerPokemonState() => new PlayerPokemonPane(controller)
        case FightingState() => new FightingPane(controller)
        case DesicionState() => DesicionPane(controller)
        case SwitchPokemonState() => new SwitchPokemonPane(controller)
        case GameOverState() => new GameOverPane(controller)

    topPane.children = new HBox(10) {
      alignment = Pos.BottomRight
      val currentPlayerName =
        if controller.game.turn == 1
        then controller.game.player1.map(_.name).getOrElse("")
        else controller.game.player2.map(_.name).getOrElse("")
      val info: Text = new Text() {
        text = currentPlayerName + " du bist dran!"
      }
      maxHeight = 10
      var undo: Button = new Button("<-") {
        onAction = _ => controller.undoMove()
      }
      var redo: Button = new Button("->") {
        onAction = _ => controller.redoMove()
      }
      children = List(info, undo, redo)
    }

  var fieldPane: VBox = new VBox() {

  }

  var menuPane: VBox = new VBox() {
    alignment = Pos.Center

  }

  var topPane: VBox = new VBox() {
    alignment = Pos.BottomRight
  }


  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {

      val soundRes = getClass.getResource("/audio.wav").toURI().toString()

      val sound = new Media(soundRes)
      val soundPlayer = new MediaPlayer(sound)
      soundPlayer.play()


      val bigBackground: Background = getBackground("/backgroundbig.png")

      private def getBackground(url: String): Background = {
        val tile: Image = new Image(url)
        val backgroundPosition = new BackgroundPosition(Side.Left, 0, false, Side.Top, 0, false)
        val backgroundImage = new BackgroundImage(tile, BackgroundRepeat.NoRepeat, BackgroundRepeat.NoRepeat, backgroundPosition,
          new BackgroundSize(1600, 480, false, false, false, false))
        new Background(new javafx.scene.layout.Background(backgroundImage))
      }

      title = "PokemonLite"
      scene = new Scene(1600, 480) {
        root = new BorderPane {
          background = bigBackground
          left = new HBox() {
            minWidth = 800
            maxHeight = 450
            children = fieldPane
          }
          center = new HBox() {
            alignment = Pos.Center
            maxWidth = 750
            minHeight = 480
            children =
              menuPane
          }
          right = new HBox() {
            alignment = Pos.BottomRight
            minWidth = 50
            children = topPane
          }


        }
      }
      update("success")
    }
  }
}


















