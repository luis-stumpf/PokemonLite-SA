package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.layout.GridPane
import scalafx.scene.image.{ Image, ImageView }
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text

import scalafx.scene.layout.Priority
import scalafx.geometry.Insets


class FieldPane extends GridPane {
  padding = Insets(10, 10, 10, 100)

  val glurakImg: Image = new Image("/pokemons/charizard.gif", 250, 250, true, true)
  val turtokImg: Image = new Image("/pokemons/blastoiseback.gif", 250, 250, true, true)
  val imgView = new ImageView( glurakImg )
  val imgView2 = new ImageView( turtokImg )
  add( imgView, 2, 0 )
  add(Text("Glurak HP: 180"), 1, 0)
  add(Text("Turtok HP: 200"), 1, 1)
  add( imgView2, 0, 1 )

}
