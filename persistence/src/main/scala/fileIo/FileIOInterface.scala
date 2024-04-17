package fileIo

import model.GameInterface

trait FileIOInterface {
  def load: GameInterface

  def save( gameSave: GameInterface ): Unit

}
