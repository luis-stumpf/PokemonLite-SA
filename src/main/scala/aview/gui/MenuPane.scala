package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import scalafx.scene.control.Button


class MenuPane extends GridPane{
  padding = Insets(100, 100, 100, 100)

  val attack1 = new Button("Attack 1"){
    padding = Insets(30, 100, 30, 100)
    margin = Insets(20, 20, 20, 20)
  }
  val attack2 = new Button("Attack 2"){
    padding = Insets(30, 100, 30, 100)
    margin = Insets(20, 20, 20, 20)
  }
  val attack3 = new Button("Attack 3"){
    padding = Insets(30, 100, 30, 100)
    margin = Insets(20, 20, 20, 20)
  }
  val attack4 = new Button("Attack 4"){
    padding = Insets(30, 100, 30, 100)
    margin = Insets(20, 20, 20, 20)
  }

  add(attack1, 0, 0)
  add(attack2, 1, 0)
  add(attack3, 0, 1)
  add(attack4, 1, 1)



}
