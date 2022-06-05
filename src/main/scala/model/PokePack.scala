package de.htwg.se.pokelite
package model

import model.impl.game.Game

case class PokePack(contents:List[Option[Pokemon]], size: Int)

object PokePack {
  def apply(contents: List[Option[Pokemon]] ): PokePack =
    PokePack(contents.take(Game.maxPokePackSize), contents.length)
}
