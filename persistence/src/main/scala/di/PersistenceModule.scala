package di

import fileIo.FileIOInterface
import fileIo.json.FileIOJson
import client.FileIORestClient
import database.DAOInterface
import database.slick.defaultImpl.SlickDAO

object PersistenceModule:
  given FileIOInterface = FileIOJson()

object PersistenceRestModule:
  given FileIOInterface = FileIORestClient()

  given DAOInterface = SlickDAO
