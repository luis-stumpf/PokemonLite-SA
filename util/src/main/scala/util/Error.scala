package util

trait Error extends Throwable

case object NoPlayerName extends Error:
  override def toString: String = "Error: There is No Player Name."

case object NothingToUndo extends Error:
  override def toString: String = "Error: There is nothing to undo."

case object NothingToRedo extends Error:
  override def toString: String = "Error: There is nothing to redo."

case object DeadPokemon extends Error:
  override def toString: String =
    "Error: Your current Pokemon is dead and not able to attack!"

case object NotAbleToSave extends Error:
  override def toString: String =
    "Error: Currently not able to save the Game, progress further!"

case object NoSaveGameFound extends Error:
  override def toString: String = "Error: Wasnt able to find save game file!"

/*
  case object NoCommandFound extends Error:
  override def toString: String = "Error: Could not find Command."
 */

case object WrongState extends Error:
  override def toString: String = "Error: The Game is in the wrong state."

case object NoInput extends Error:
  override def toString: String = "Error: Please Enter a valid input."

case object NoPokemonSelected extends Error:
  override def toString: String = "Error: You haven't selected a valid Pokemon."

case class NotEnoughPokemonSelected( amount: Int ) extends Error:
  override def toString: String =
    "Error: You have selected \"" + amount + "\" valid Pokemon, but 3 is required."

case class NoValidAttackSelected( input: String ) extends Error:
  override def toString: String =
    "Error: The Input \"" + input + "\" is not valid. Select a attack between 1 and 4."

case class WrongInput( input: String ) extends Error:
  override def toString: String =
    "Error: The Input \"" + input + "\" is not valid."

case object NoDesicionMade extends Error:
  override def toString: String = "Error: Couldnt find a valid Desicion."

case object NoPlayerExists extends Error:
  override def toString: String = "Error: There Player."

case object NoPlayerToRemove extends Error:
  override def toString: String = "Error: No Player to remove."

case class NameTooLong( input: String ) extends Error:
  override def toString: String =
    "Error: The Name \"" + input + "\" is too long."

case object HorriblePlayerNameError extends Error:
  override def toString: String =
    "For some reason Player 2 has a name but Player 1 doesnt. Everything is F****d!"

case object HorriblePokemonSelectionError extends Error:
  override def toString: String =
    "For some reason Player 2 has Pokemon but Player 1 doesnt. Everything is F****d!"

case object CanNotUndoSave extends Error:
  override def toString: String =
    "You cant undo a save command."

case object CanNotUndoLoad extends Error:
  override def toString: String =
    "You cant undo a load command."

case object CouldNotLoadGame extends Error:
  override def toString: String =
    "Could not load the game."
