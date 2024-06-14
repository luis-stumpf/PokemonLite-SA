package controller

import service.ControllerRestService
import controller.impl.Controller
import controller.ControllerInterface
import di.ControllerRestServerModule
import service.ControllerKafkaService

object ControllerRestApi {

  @main() def run() = {
    val controllerService = new ControllerRestService( using
      ControllerRestServerModule.given_ControllerInterface
    )

    val controllerKafkaService = new ControllerKafkaService( using
      ControllerRestServerModule.given_ControllerInterface
    )
    // controllerService.start()
    controllerKafkaService.start()
  }
}
