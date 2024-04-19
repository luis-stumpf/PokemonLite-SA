package persistence

import service.PersistenceRestService

object PersistenceRestApi {
  val persistenceService = new PersistenceRestService()
  @main() def run() = {
    persistenceService.start()
  }
}
