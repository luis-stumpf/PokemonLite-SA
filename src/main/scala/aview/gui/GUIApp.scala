package de.htwg.se.pokelite
package aview.gui

import util.Observer
import controller.Controller




class GUIApp(val controller: Controller) extends Observer{

  val gui:GUI = new GUI(this, controller)
  val thread:Thread = new Thread{
    override def run() : Unit = {
      gui.main( Array())
    }
  }
  thread.start()
  
  controller.add(this)


  override def update : Unit = controller.field



}
