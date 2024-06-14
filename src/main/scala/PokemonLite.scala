package main

import controller.ControllerRestApi
import persistence.PersistenceRestApi
import service.TuiRestService
import service.GuiRestService
import service.TuiKafkaService

object PokemonLite extends App {

  val controllerService = ControllerRestApi.run()
  val persistenceService = PersistenceRestApi.run()

  // TuiService
  // TuiRestService
  TuiKafkaService

  // GuiService
  // GuiRestService.main( Array.empty )

}
