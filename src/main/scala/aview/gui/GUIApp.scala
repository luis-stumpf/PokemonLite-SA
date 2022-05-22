package de.htwg.se.pokelite
package aview.gui

import util.Observer
import controller.Controller

class GUIApp(val controller: Controller) extends Observer{
  
  controller.add(this)

  override def update : Unit = ???
  
  

}
