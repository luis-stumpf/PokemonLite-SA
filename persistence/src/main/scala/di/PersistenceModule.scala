package di

import fileIo.FileIOInterface
import fileIo.json.FileIOJson

object PersistenceModule:
  given FileIOInterface = FileIOJson()
