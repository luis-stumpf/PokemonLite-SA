package de.htwg.se.pokelite
package model

import model.*

case class Field(width : Int, player1 : PokePlayer, player2 : PokePlayer, isControlledBy : Int = 1):
  def mesh(height : Int = 3) : String = row() + printPlayer1Stats() + col( height ) + printPlayer2Stats() + row()

  def row() : String = "+" + ( "-" * width + "+" ) * 2 + "\n"

  def col(height : Int) : String = ( ( "|" + " " * width ) * 2 + "|\n" ) * height

  def printPlayer1Stats() : String = printTopPlayer() + cleanSite() + printTopPokemon()

  def printPlayer2Stats() : String = printBottomPokemon() + printBottomPlayer() + cleanSite()

  def calcSpace(start : Double, element : String) : Int = ( width * start ).floor.toInt - element.length

  def calcSpace(start : Double) : Int = ( width * start ).floor.toInt

  def cleanSite() : String = "|" + " " * width + "|\n"

  def printTopPlayer() : String = "|" + " " * calcSpace( 0.9, player1.name ) + player1.name + " " * calcSpace( 0.1 )

  def printTopPokemon() : String = "|" + " " * calcSpace( 0.9, player1.pokemons.contents.apply( player1.currentPoke ).map( _.toString ).getOrElse( "" ) ) + player1.pokemons.contents.apply( player1.currentPoke ).map( _.toString ).getOrElse( "" ) + " " * calcSpace( 0.1 ) + printTopAttacks()

  def printBottomPlayer() : String = "|" + " " * calcSpace( 0.1 ) + player2.name + " " * calcSpace( 0.9, player2.name )

  def printBottomPokemon() : String = "|" + " " * calcSpace( 0.1 ) + player2.pokemons.contents.apply( player2.currentPoke ).map( _.toString ).getOrElse( "" ) + " " * calcSpace( 0.9, player2.pokemons.contents.apply( player2.currentPoke ).map( _.toString ).getOrElse( "" ) ) + printBottomAttacks()

  def printTopAttacks() : String = if ( isControlledBy == 1 ) printTopAttacksOf( player1.pokemons.contents.apply( player1.currentPoke ) ) else printTopAttacksOf( player2.pokemons.contents.apply( player2.currentPoke ) )

  def printBottomAttacks() : String = if ( isControlledBy == 1 ) printBottomAttacksOf( player1.pokemons.contents.apply( player1.currentPoke ) ) else printBottomAttacksOf( player2.pokemons.contents.apply( player2.currentPoke ) )

  def printTopAttacksOf(pokemon : Option[ Pokemon ]) : String =
    if ( pokemon.isDefined )
      "|" + " " * calcSpace( 0.1 ) + "1. " + pokemon.get.pType.attacks.head.name + " " * ( calcSpace( 0.4, pokemon.get.pType.attacks.head.name ) - 3 ) +
        "2. " + pokemon.get.pType.attacks.apply( 1 ).name + " " * ( calcSpace( 0.5, pokemon.get.pType.attacks.apply( 1 ).name ) - 3 ) + "|\n"
    else cleanSite()

  def printBottomAttacksOf(pokemon : Option[ Pokemon ]) : String =
    if ( pokemon.isDefined )
      "|" + " " * calcSpace( 0.1 ) + "3. " + pokemon.get.pType.attacks.apply( 2 ).name + " " * ( calcSpace( 0.4, pokemon.get.pType.attacks.apply( 2 ).name ) - 3 ) +
        "4. " + pokemon.get.pType.attacks.apply( 3 ).name + " " * ( calcSpace( 0.5, pokemon.get.pType.attacks.apply( 3 ).name ) - 3 ) + "|\n"
    else cleanSite()


  def setPlayerNameTo(newName : String) : Field = if ( player1.name == "" ) copy( player1 = player1.setPokePlayerNameTo( newName ) ) else copy( player2 = player2.setPokePlayerNameTo( newName ) )

  def setPokemonTo(newPokemons : List[ Option[ Pokemon ] ]) : Field =
    if ( player1.pokemons.contents.head.isEmpty )
      copy( player1 = player1.setPokemonTo( new PokePack[ Option[ Pokemon ] ]( newPokemons ) ) )
    else
      copy( player2 = player2.setPokemonTo( new PokePack[ Option[ Pokemon ] ]( newPokemons ) ) )

  def setNextTurn() : Field = if ( isControlledBy == 1 ) copy( isControlledBy = 2 ) else copy( isControlledBy = 1 )

  def attack(attack : Int) : Field = AttackPlayerStrat.strategy( attack )

  def attackInv(attack : Int) : Field = AttackInvStrat.strategy( attack )

  def changePokemon(i : Int) : Field = ChangePokemonStrat.strategy( i )

  object ChangePokemonStrat {
    val strategy = if ( isControlledBy == 1 ) strategy1 else strategy2

    def strategy1(i : Int) = copy( player1 = player1.copy( currentPoke = i ) )

    def strategy2(i : Int) = copy( player2 = player2.copy( currentPoke = i ) )
  }

  def getCurrentPokemons : List[ Pokemon ] =
    var list : List[ Pokemon ] = List()
    if ( this.isControlledBy == 1 )
      for ( content <- this.player1.pokemons.contents ) yield content match
        case Some( b ) => list = list :+ b
        case None =>
    else
      for ( content <- this.player2.pokemons.contents ) yield content match {
        case Some( b ) => list = list :+ b
        case None =>
      }
    list.take( POKEPACK_SIZE )


  override def toString : String = mesh()

  object AttackPlayerStrat {

    var strategy = if ( isControlledBy == 1 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      val mult = getDamageMultiplikator( player1.pokemons.contents.apply( player1.currentPoke ).get.pType.pokemonArt, player2.pokemons.contents.apply( player2.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player2 = player2.copy( pokemons = player2.pokemons.copy( player2.pokemons.contents.updated( player2.currentPoke, player2.pokemons.contents.apply( player2.currentPoke ).get.changeHp( player1.pokemons.contents.apply( player1.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) )
      if player2.checkForDead() then
        System.exit(0)
      kopie
    def strategy2(attack : Int) =
      val mult = getDamageMultiplikator( player2.pokemons.contents.apply( player2.currentPoke ).get.pType.pokemonArt, player1.pokemons.contents.apply( player1.currentPoke ).get.pType.pokemonArt )
      val kopie = copy(
        player1 = player1.copy( pokemons = player1.pokemons.copy( player1.pokemons.contents.updated( player1.currentPoke, player1.pokemons.contents.apply( player1.currentPoke ).get.changeHp( player2.pokemons.contents.apply( player2.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) )
      if player1.checkForDead() then
        System.exit(0)
      kopie
  }

  object AttackInvStrat {
    var strategy = if ( isControlledBy == 1 ) strategy1 else strategy2

    def strategy1(attack : Int) =
      var mult = getDamageMultiplikator( player1.pokemons.contents.apply( player1.currentPoke ).get.pType.pokemonArt, player2.pokemons.contents.apply( player2.currentPoke ).get.pType.pokemonArt )
      copy(
        player2 = player2.copy( pokemons = player2.pokemons.copy( player2.pokemons.contents.updated( player2.currentPoke, Some( player2.pokemons.contents.apply( player2.currentPoke ).get.changeHpInv( player1.pokemons.contents.apply( player1.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) )

    def strategy2(attack : Int) =
      var mult = getDamageMultiplikator( player2.pokemons.contents.apply( player2.currentPoke ).get.pType.pokemonArt, player1.pokemons.contents.apply( player1.currentPoke ).get.pType.pokemonArt )
      copy( player1 = player1.copy( pokemons = player1.pokemons.copy( player1.pokemons.contents.updated( player1.currentPoke, Some( player1.pokemons.contents.apply( player1.currentPoke ).get.changeHpInv( player2.pokemons.contents.apply( player2.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) ) )


  }
