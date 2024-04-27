package di

import model.GameInterface
import model.impl.game.Game

object ModelModule:
  given GameInterface = Game()
