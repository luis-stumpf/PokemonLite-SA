package fileIo

import model.GameInterface
import play.api.libs.json.JsValue
import scala.util.Try

trait FileIOInterface {
  def load: GameInterface

  def save( gameSave: GameInterface ): Unit

  def save( gameJson: JsValue ): Unit
}
