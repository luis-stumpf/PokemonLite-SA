package de.htwg.se.pokelite
package model

import model.impl.game.Game

import com.google.inject.Inject
import scala.xml.Node

case class PokePack (contents:List[Option[Pokemon]], size: Int):
  def checkIfAllPokemonAreDead = contents.take(Game.maxPokePackSize).forall(x => x.get.isDead)
  def toXML:Node =
    <PokePack>
      <contents>{contents.map(
        e => 
          <entry>{e.map(_.toXML).getOrElse("None")}</entry>
      )}</contents>
      <size>{size.toString}</size>
    </PokePack>

object PokePack {
  def apply(contents: List[Option[Pokemon]] ): PokePack =
    PokePack(contents.take(Game.maxPokePackSize), contents.length)
}
