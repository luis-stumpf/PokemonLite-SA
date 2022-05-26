package de.htwg.se.pokelite
package aview.gui

import util.Observer
import controller.Controller

import scalafx.scene.layout.{Background, BackgroundImage, BackgroundPosition, BackgroundSize, BackgroundRepeat}
import scalafx.scene.image.Image
import scalafx.geometry.Side


object GUIApp {
  val battleBackground : Background = getBackground( "/backgroundbig.png" )

  private def getBackground(url : String) : Background = {
    val tile : Image = new Image( url )
    val backgroundPosition = new BackgroundPosition(Side.Left, 0, false, Side.Top, 0, false)
    val backgroundImage = new BackgroundImage( tile, BackgroundRepeat.NoRepeat, BackgroundRepeat.NoRepeat, backgroundPosition,
      new BackgroundSize( 1600, 480, false, false, false, false ) )
    new Background( new javafx.scene.layout.Background( backgroundImage ) )
  }
}


class GUIApp(val controller : Controller) extends Observer {

  val gui : GUI = new GUI( this, controller )
  val thread : Thread = new Thread {
    override def run() : Unit = {
      gui.main( Array() )
    }
  }
  thread.start()

  controller.add( this )

  def exit():Unit = gui.stopApp()

  override def update : Unit =
    gui.update(controller.game.state)

}
