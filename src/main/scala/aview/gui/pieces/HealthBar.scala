package de.htwg.se.pokelite
package aview.gui.pieces

import de.htwg.se.pokelite.controller.impl.Controller
import de.htwg.se.pokelite.model.states.{InitPlayerPokemonState, InitPlayerState, InitState}
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.layout.VBox
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Paint
import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Cylinder
import scalafx.scene.layout.HBox
import scalafx.scene.text.{Font, Text}

case class HealthBar(controller: Controller, turn: Int) extends VBox {

  padding = Insets(80, 0, 0, 0)

  val stackPane = new StackPane() {
    alignment = Pos.CenterLeft

    val healthBar = new Rectangle() {
      height = 10

      width = calcHealthBar
      fill = Paint.valueOf("red")
    }

    /*
    val healthBarBackgournd = new Rectangle() {
      height = 12
      width = 130
      fill = Paint.valueOf("white")
    }
    */
    children = List(healthBar)
  }


  children = List(status, stackPane)

  def status: Text =
    var text = new Text()
    if (turn == 1) then
      text = new Text(controller.game.player1.map(_.pokemons.contents.apply(controller.game.player1.get.currentPoke).map(_.toString).getOrElse("")).getOrElse("")) {
      font = Font.font(15)
    }
    else
      text = new Text(controller.game.player2.map(_.pokemons.contents.apply(controller.game.player2.get.currentPoke).map(_.toString).getOrElse("")).getOrElse("")) {
        font = Font.font(15)
      }
    text



  def calcHealthBar: Double =

    var currentHealth = 0
    var maxHealth = 1
    var res = 0
    if (turn == 1) then
      currentHealth = controller.game.player1.map(_.pokemons.contents.apply(controller.game.player1.get.currentPoke).map(_.getHp).getOrElse(0)).getOrElse(0)
      maxHealth = controller.game.player1.map(_.pokemons.contents.apply(controller.game.player1.get.currentPoke).map(_.pType.hp).getOrElse(1)).getOrElse(1)
    else
      currentHealth = controller.game.player2.map(_.pokemons.contents.apply(controller.game.player2.get.currentPoke).map(_.getHp).getOrElse(0)).getOrElse(0)
      maxHealth = controller.game.player2.map(_.pokemons.contents.apply(controller.game.player2.get.currentPoke).map(_.pType.hp).getOrElse(1)).getOrElse(1)
    (currentHealth.toFloat / maxHealth) * 130


}
