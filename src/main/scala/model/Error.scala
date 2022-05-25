package de.htwg.se.pokelite
package model

trait Error extends Throwable

case object NoPlayerName extends Error
