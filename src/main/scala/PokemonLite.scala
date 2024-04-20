package main

import di.PokemonLiteModule.given_ControllerInterface as Controller
import controller.ControllerRestApi
import persistence.PersistenceRestApi
import service.TuiService
import service.TuiRestService
import service.GuiService
import service.GuiRestService

object PokemonLite extends App {

  val controller = Controller
  val controllerService = ControllerRestApi.run()
  val persistenceService = PersistenceRestApi.run()

  // TuiService
  // TuiRestService

  // GuiService
  GuiRestService

}
