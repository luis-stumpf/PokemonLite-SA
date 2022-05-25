package de.htwg.se.pokelite
package model

trait Error extends Throwable

case object NoPlayerName extends Error
case object NothingToUndo extends Error
case object NothingToRedo extends Error
case object NoCommandFound extends Error
case object NoInput extends Error