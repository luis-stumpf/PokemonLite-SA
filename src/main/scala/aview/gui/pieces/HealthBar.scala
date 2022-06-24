package de.htwg.se.pokelite
package aview.gui.pieces

import controller.ControllerInterface
import controller.impl.Controller
import model.states.{InitPlayerPokemonState, InitPlayerState, InitState}

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout.*
import scalafx.scene.paint.Paint
import scalafx.scene.shape.{Cylinder, Rectangle}
import scalafx.scene.text.{Font, Text}

case class HealthBar(controller: ControllerInterface, turn: Int) extends StackPane() {

  padding = Insets(80, 0, 0, 0)


  val stackPane = new StackPane() {
    val healthBar = new Rectangle() {
      alignment = Pos.CenterLeft
      height = 10
      width = calcHealthBar
      fill = Paint.valueOf("lightgreen")
      margin = Insets(0, 0, 1, 2)
    }


    val healthBarBackground = new Rectangle() {
      alignment = Pos.CenterLeft
      height = 12
      width = 132
      style = "-fx-fill: #d4cdba; -fx-stroke: #33312a; -fx-stroke-width: 2;"
    }

    children = List(healthBarBackground, healthBar)
  }

  val overlay = new VBox() {
    margin = Insets(5)
    alignment = Pos.CenterLeft

    children = List(status, stackPane)
  }

  val underlay = new Rectangle() {
    alignment = Pos.CenterLeft
    width = 170
    height = 40
    style = "-fx-fill: #a68d70; -fx-stroke: #5a452e; -fx-stroke-width: 2;"
  }


  children = List(underlay, overlay)

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
