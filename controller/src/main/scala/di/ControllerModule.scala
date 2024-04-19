package di

import controller.ControllerInterface
import controller.impl.Controller
import client.ControllerRestClient

object ControllerModule:
  given ControllerInterface = Controller( using
    PersistenceModule.given_FileIOInterface
  )

object ControllerRestModule:
  given ControllerInterface = ControllerRestClient( using
    PersistenceModule.given_FileIOInterface
  )
