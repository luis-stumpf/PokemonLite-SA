package de.htwg.se.pokelite
package aview.gui

import util.Observer
import controller.Controller
import model.states.*

import de.htwg.se.pokelite.model.states.{DesicionState, FightingState, GameOverState, InitPlayerPokemonState, InitPlayerState, InitState, SwitchPokemonState}
import scalafx.application.Platform
import scalafx.scene.layout.{Background, BackgroundImage, BackgroundPosition, BackgroundRepeat, BackgroundSize}
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
  controller.add( this )

  override def update : Unit =
    controller.game.state match
      case InitState() => thread.start()
      case InitPlayerState() => gui.setInitPlayerPane()
      case InitPlayerPokemonState() =>
      case DesicionState() =>
      case FightingState() =>
      case SwitchPokemonState() =>
      case GameOverState() => gui.stopApp()

}
