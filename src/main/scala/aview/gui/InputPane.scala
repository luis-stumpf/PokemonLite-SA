package de.htwg.se.pokelite
package aview.gui

import scalafx.scene.control.Button
import scalafx.geometry.Insets
import scalafx.scene.layout.VBox
import scalafx.scene.control.TextField

class InputPane extends VBox {
  margin = Insets( 200, 500, 100, 200 )
  children = List(
    new TextField(),
    new Button( "Bestätigen" ) )
}
