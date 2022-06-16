package de.htwg.se.pokelite
package model

import model.impl.game.Game

import com.google.inject.Inject

case class PokePack (contents:List[Option[Pokemon]], size: Int):
  def checkIfAllPokemonAreDead = contents.take(Game.maxPokePackSize).forall(x => x.get.isDead)

object PokePack {
  def apply(contents: List[Option[Pokemon]] ): PokePack =
    PokePack(contents.take(Game.maxPokePackSize), contents.length)
}
