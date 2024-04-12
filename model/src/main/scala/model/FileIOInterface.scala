package model

trait FileIOInterface {
  def load: GameInterface

  def save( gameSave: GameInterface ): Unit

}
