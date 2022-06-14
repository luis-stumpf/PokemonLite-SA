package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import controller.impl.Controller

import de.htwg.se.pokelite.controller.ControllerInterface
import scalafx.scene.image.{Image, ImageView}


case class FightingPane(controller: ControllerInterface) extends GridPane{


  def getAttackName(i: Int):String =
    if(controller.game.turn == 1)
      controller.game.player1.get.pokemons.contents.apply(controller.game.player1.get.currentPoke).get.pType.attacks.apply(i).name
    else
      controller.game.player2.get.pokemons.contents.apply(controller.game.player2.get.currentPoke).get.pType.attacks.apply(i).name


  val attack1 = new Button(getAttackName(0)){
    margin = Insets(20)
    minWidth = 200
    minHeight = 60
    onAction = _ => controller.attackWith("1")
  }
  val attack2 = new Button(getAttackName(1)){
    margin = Insets(20)
    minWidth = 200
    minHeight = 60
    onAction = _ => controller.attackWith("2")
  }
  val attack3 = new Button(getAttackName(2)){
    margin = Insets(20)
    minWidth = 200
    minHeight = 60
    onAction = _ => controller.attackWith("3")
  }
  val attack4 = new Button(getAttackName(3)){
    margin = Insets(20)
    minWidth = 200
    minHeight = 60
    onAction = _ => controller.attackWith("4")
  }

  add(attack1, 0, 0)
  add(attack2, 1, 0)
  add(attack3, 0, 1)
  add(attack4, 1, 1)



}