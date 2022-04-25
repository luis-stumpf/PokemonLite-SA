package de.htwg.se.pokelite
package aview

import controller.Controller
import util.Observer


class TUI(controller: Controller) extends Observer:
  controller.add(this)

  override def update = ???


