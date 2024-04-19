package di

import controller.ControllerInterface
import controller.impl.Controller

object ControllerModule:
  given ControllerInterface = Controller( using
    PersistenceModule.given_FileIOInterface
  )
