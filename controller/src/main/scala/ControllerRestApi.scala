import service.ControllerRestService
import controller.impl.Controller
import controller.ControllerInterface
object ControllerRestApi {
  given controller: ControllerInterface = Controller()
  val controllerService = new ControllerRestService()
  @main() def run() = {
    controllerService.start()
  }
}
