package de.htwg.se.pokelite
package model

import model.impl.game.Game

import com.google.inject.Inject
import play.api.libs.json.{JsValue, Json}

import scala.xml.Node
import scala.xml.NodeSeq.fromSeq

object PokePack {
  def apply(contents: List[Option[Pokemon]]): PokePack =
    PokePack(contents.take(Game.maxPokePackSize), contents.length)

  def fromXML(node: Node): PokePack =
    val contentNodes = (node \\ "entry")

    PokePack(
      contents = contentNodes.map(n => Pokemon.fromXML(n)).toList,
      size = (node \\ "size").text.replace(" ", "").toInt
    )


  def fromJson(json: JsValue): PokePack =
    val contentNodes = (json \ "contents").validate[List[JsValue]].get

    PokePack(
      contents = contentNodes.map(n => Pokemon.fromJson(n)).toList,
      size = (json \ "size").as[Int]
    )
}

case class PokePack(contents: List[Option[Pokemon]], size: Int):
  def checkIfAllPokemonAreDead = contents.take(Game.maxPokePackSize).forall(x => x.get.isDead)

  def toXML: Node =
    <PokePack>
      <contents>
        {contents.map(
        e =>
          <entry>
            {e.map(_.toXML).getOrElse("None")}
          </entry>
      )}
      </contents>
      <size>
        {size.toString}
      </size>
    </PokePack>

  def toJson: JsValue =
    Json.obj(
      "contents" -> Json.toJson(contents.map(e => e.get.toJson)),
      "size" -> Json.toJson(size)
    )

