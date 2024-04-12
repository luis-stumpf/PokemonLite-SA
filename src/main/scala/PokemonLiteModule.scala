import com.google.inject.AbstractModule
import com.google.inject.name.Names
import controller.*
import controller.impl.*
import model.PokePlayerInterface
import model.impl.pokePlayer.PokePlayer
import tui.MatrixField
import model.FieldInterface
import model.GameInterface
import model.impl.game.Game

class PokemonLiteModule extends AbstractModule {

  override def configure(): Unit =
    bind( classOf[ControllerInterface] ).to( classOf[Controller] )
    bind( classOf[PokePlayerInterface] ).to( classOf[PokePlayer] )
    bind( classOf[GameInterface] ).to( classOf[Game] )

    /*
    bind(classOf[FileIOInterface]).to(classOf[model.impl.fileIo.xml.FileIO])
    bind( classOf[FileIOInterface] ).to(
      classOf[model.impl.fileIo.json.FileIO]
    )
     */
}
