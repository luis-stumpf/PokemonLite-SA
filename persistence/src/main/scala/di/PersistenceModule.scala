package di

import fileIo.FileIOInterface
import fileIo.json.FileIOJson
import client.FileIORestClient

object PersistenceModule:
  given FileIOInterface = FileIOJson()

object PersistenceRestModule:
  given FileIOInterface = FileIORestClient()
