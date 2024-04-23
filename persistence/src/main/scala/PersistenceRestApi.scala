package persistence

import service.PersistenceRestService
import di.PersistenceModule

object PersistenceRestApi {
  val persistenceService = new PersistenceRestService( using
    PersistenceModule.given_FileIOInterface
  )
  @main() def run() = {
    persistenceService.start()
  }
}
