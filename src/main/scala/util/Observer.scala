package de.htwg.se.pokelite
package util

trait Observer:
  def update(message: String): Unit

trait Observable:
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer) = subscribers = subscribers :+ s

  def remove(s: Observer) = subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers(message: String = "success") = subscribers.foreach(o => o.update(message))
