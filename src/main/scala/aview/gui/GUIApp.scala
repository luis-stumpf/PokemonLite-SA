package de.htwg.se.pokelite
package aview.gui

import util.{ EndEvent, Event, MidEvent, Observer, PreEvent }
import controller.Controller

object GUIApp{


}

class GUIApp(val controller: Controller) extends Observer, Event{

  val gui:GUI = new GUI(this, controller)
  val thread:Thread = new Thread{
    override def run() : Unit = {
      gui.main( Array())
    }
  }

  
  controller.add(this)


  override def update(e: Event) : Unit =
    e match{
      case EndEvent() => exit()
      case MidEvent() => thread.start()
      case PreEvent() => thread.start()
    }

  def exit( ):Unit = gui.stopApp()


}
