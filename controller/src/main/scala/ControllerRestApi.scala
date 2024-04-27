package controller

import service.ControllerRestService
import controller.impl.Controller
import controller.ControllerInterface
import di.ControllerRestServerModule

object ControllerRestApi {

  val controllerService = new ControllerRestService( using
    ControllerRestServerModule.given_ControllerInterface
  )
  @main() def run() = {
    controllerService.start()
  }
}
