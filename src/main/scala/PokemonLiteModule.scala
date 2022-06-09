package de.htwg.se.pokelite

import com.google.inject.AbstractModule
import controller.ControllerInterface
import controller.impl.Controller
import model.{FieldInterface, GameInterface, PokePlayerInterface}
import model.impl.field.Field
import model.impl.pokePlayer.PokePlayer
import model.impl.game.Game


class PokemonLiteModule extends AbstractModule{

  override def configure(): Unit =
    bind(classOf[ControllerInterface]).to(classOf[Controller])
    bind(classOf[PokePlayerInterface]).to(classOf[PokePlayer])
    bind(classOf[FieldInterface]).to(classOf[Field])
    bind(classOf[GameInterface]).to(classOf[Game])

}
