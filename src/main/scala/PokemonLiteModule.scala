package de.htwg.se.pokelite

import com.google.inject.AbstractModule
import controller.ControllerInterface
import controller.impl.Controller
import model.{FieldInterface, PokePlayerInterface}
import model.impl.field.Field
import model.impl.pokePlayer.PokePlayer


class PokemonLiteModule extends AbstractModule{

  override def configure(): Unit =
    bind(classOf[ControllerInterface]).to(classOf[Controller])
    bind(classOf[PokePlayerInterface]).to(classOf[PokePlayer])
    bind(classOf[FieldInterface]).to(classOf[Field])

}
