package controller

import service.ControllerRestService
import controller.impl.Controller
import controller.ControllerInterface
import di.ControllerRestServerModule

object ControllerRestApi {

  @main() def run() = {
    val controllerService = new ControllerRestService( using
      ControllerRestServerModule.given_ControllerInterface
    )
    controllerService.start()
  }
}
