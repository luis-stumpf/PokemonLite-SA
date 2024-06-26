package tui

import model.PokePlayerInterface
import model.FieldInterface
import model.impl.pokePlayer.PokePlayer
import com.google.inject.Inject
import org.checkerframework.checker.units.qual.h
import scala.util.chaining.scalaUtilChainingOps
import model.GameInterface
import model.impl.game.Game

case class MatrixField( width: Int, height: Int = 15, game: GameInterface )
    extends FieldInterface {

  def base(): Array[Array[String]] = {
    Array
      .ofDim[String]( height, width )
      .zipWithIndex
      .map { case ( row, index ) =>
        if (index == 0) {
          row.zipWithIndex.map( ( cell, cellIndex ) =>
            if (isEdgeCell( index, cellIndex ) || isMiddleColumn( cellIndex ))
              "+"
            else "-"
          )
        } else if (index == height - 1) {
          row.zipWithIndex.map( ( cell, cellIndex ) =>
            if (isEdgeCell( index, cellIndex ) || isMiddleColumn( cellIndex ))
              "+"
            else "-"
          )
        } else {
          row.zipWithIndex.map { case ( cell, cellIndex ) =>
            if (
              cellIndex == 0 || cellIndex == width - 1 || isMiddleColumn(
                cellIndex
              )
            )
              "|"
            else " "
          }
        }
      }
  }

  def insertLeftSiteData(
    matrix: Array[Array[String]]
  ): Array[Array[String]] = {
    val playerData = List(
      ( game.player1.getOrElse( PokePlayer( "" ) ).name, 2, 0.7 ),
      ( game.player2.getOrElse( PokePlayer( "" ) ).name, height - 3, 0.1 ),
      (
        game.player1
          .getOrElse( PokePlayer( "" ) )
          .currentPokemon
          .getOrElse( "" )
          .toString(),
        4,
        0.7
      ),
      (
        game.player2
          .getOrElse( PokePlayer( "" ) )
          .currentPokemon
          .getOrElse( "" )
          .toString(),
        height - 5,
        0.1
      )
    )

    playerData.foldLeft( matrix ) { case ( matrix, ( name, row, start ) ) =>
      replaceWithCharRecursive( matrix, name, row, calcSpace( start ) )
    }
  }

  def insertRightSiteData(
    matrix: Array[Array[String]]
  ): Array[Array[String]] = {
    val currentPlayer =
      if (game.turn == 1) game.player1.getOrElse( PokePlayer( "" ) )
      else game.player2.getOrElse( PokePlayer( "" ) )
    currentPlayer.pokemons.contents( currentPlayer.currentPoke ) match {
      case Some( pokemon ) =>
        val attackNames = pokemon.pType.attacks.zipWithIndex.map {
          case ( attack, index ) =>
            (
              s"${index + 1}. ${attack.name}",
              if (index < 2) 4 else height - 5,
              if (index % 2 == 0) 0.1 else 0.7
            )
        }
        attackNames.foldLeft( matrix ) {
          case ( matrix, ( name, row, start ) ) =>
            replaceWithCharRecursive(
              matrix,
              name,
              row,
              calcSpaceRight( start )
            )
        }
      case None => matrix
    }
  }

  def isEdgeCell( rowIndex: Int, cellIndex: Int ): Boolean =
    ( rowIndex == 0 || rowIndex == height - 1 ) && cellIndex == 0 || ( rowIndex == 0 || rowIndex == height - 1 ) && cellIndex == width - 1

  def isMiddleColumn( cellIndex: Int ): Boolean = cellIndex == width / 2

  def calcHalfWidth( start: Double ): Int = ( width / 2 * start ).floor.toInt

  def calcSpace( start: Double ): Int = calcHalfWidth( start )

  def calcSpaceRight( start: Double ): Int = calcHalfWidth( 1 + start )

  def replaceWithCharRecursive(
    arr: Array[Array[String]],
    string: String,
    row: Int,
    index: Int,
    charIndex: Int = 0
  ): Array[Array[String]] = {
    if (index >= arr( row ).length || charIndex >= string.length) {
      arr
    } else {
      arr( row )( index ) = string.charAt( charIndex ).toString()
      replaceWithCharRecursive( arr, string, row, index + 1, charIndex + 1 )
    }

  }

  def mesh() =
    ( insertLeftSiteData andThen insertRightSiteData )( base() )
      .map( _.mkString( "" ) )
      .mkString( "\n" ) + "\n"

  override def toString(): String = mesh()
}
