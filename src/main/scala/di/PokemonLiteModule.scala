package di

import model.GameInterface
import di.ModelModule.given_GameInterface as Game
import di.ControllerModule.given_ControllerInterface as Controller
import controller.ControllerInterface

object PokemonLiteModule:
  given ControllerInterface = Controller
  given GameInterface = Game
