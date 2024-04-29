package fileIo

import model.GameInterface
import play.api.libs.json.JsValue
import scala.util.Try

trait FileIOInterface {
  def load: GameInterface

  def save( gameSave: GameInterface ): Unit

  def save( gameJson: JsValue ): Unit

  def update( gameUpdate: GameInterface ): Unit

  def delete: Unit
}
