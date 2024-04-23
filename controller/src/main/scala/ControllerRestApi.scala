import service.ControllerRestService
import controller.impl.Controller
import controller.ControllerInterface
import di.ControllerModule.given_ControllerInterface

object ControllerRestApi {

  val controllerService = new ControllerRestService()
  @main() def run() = {
    controllerService.start()
  }
}
