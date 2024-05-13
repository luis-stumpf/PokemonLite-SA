package service

import di.ControllerRestModule
import di.ControllerModule
import gui.GUI
import scalafx.application.JFXApp3
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Label

/*
object GuiService extends App:
  GUI( using ControllerModule.given_ControllerInterface ).main( Array.empty )
 */

object GuiRestService:
  def main( args: Array[String] ): Unit =
    GUI( using ControllerRestModule.given_ControllerInterface ).main(
      Array.empty
    )
