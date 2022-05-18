package de.htwg.se.pokelite
package model


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

  def printTopPokemon() : String = "|" + " " * calcSpace( 0.9, player1.pokemons.apply( player1.currentPoke ).map( _.toString ).getOrElse( "" ) ) + player1.pokemons.apply( player1.currentPoke ).map( _.toString ).getOrElse( "" ) + " " * calcSpace( 0.1 ) + printTopAttacks()

  def printBottomPlayer() : String = "|" + " " * calcSpace( 0.1 ) + player2.name + " " * calcSpace( 0.9, player2.name )

  def printBottomPokemon() : String = "|" + " " * calcSpace( 0.1 ) + player2.pokemons.apply( player2.currentPoke ).map( _.toString ).getOrElse( "" ) + " " * calcSpace( 0.9, player2.pokemons.apply( player2.currentPoke ).map( _.toString ).getOrElse( "" ) ) + printBottomAttacks()

  def printTopAttacks() : String = if ( isControlledBy == 1 ) printTopAttacksOf( player1.pokemons.apply( player1.currentPoke ) ) else printTopAttacksOf( player2.pokemons.apply( player2.currentPoke ) )

  def printBottomAttacks() : String = if ( isControlledBy == 1 ) printBottomAttacksOf( player1.pokemons.apply( player1.currentPoke ) ) else printBottomAttacksOf( player2.pokemons.apply( player2.currentPoke ) )

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

  def setPokemonTo(newPokemons : List[ Option[ Pokemon ] ]) : Field = if ( player1.pokemons.head.isEmpty ) copy( player1 = player1.setPokemonTo( newPokemons ) ) else copy( player2 = player2.setPokemonTo( newPokemons ) )

  def setNextTurn() : Field = if ( isControlledBy == 1 ) copy( isControlledBy = 2 ) else copy( isControlledBy = 1 )

  def attack(attack : Int) : Field = if ( isControlledBy == 1 )
    var mult = calcDamage(player1.pokemons.apply(player1.currentPoke).get.pType.pokemonArt, player2.pokemons.apply(player2.currentPoke).get.pType.pokemonArt)
    copy(
    player2 = player2.copy( pokemons = player2.pokemons.updated( player2.currentPoke, Some( player2.pokemons.apply( player2.currentPoke ).get.changeHp( player2.pokemons.apply( player2.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) )
  else
    var mult = calcDamage(player2.pokemons.apply(player2.currentPoke).get.pType.pokemonArt, player1.pokemons.apply(player1.currentPoke).get.pType.pokemonArt)
    copy(
      player1 = player1.copy( pokemons = player1.pokemons.updated( player1.currentPoke, Some( player1.pokemons.apply( player1.currentPoke ).get.changeHp( player1.pokemons.apply( player1.currentPoke ).get.pType.attacks.apply( attack ), mult ) ) ) ) )

  def attackInv(attack : Int) : Field = if ( isControlledBy == 1 ) copy(
    player2 = player2.copy( pokemons = player2.pokemons.updated( player2.currentPoke, Some( player2.pokemons.apply( player2.currentPoke ).get.changeHpInv( player2.pokemons.apply( player2.currentPoke ).get.pType.attacks.apply( attack ) ) ) ) ) )
  else copy( player1 = player1.copy( pokemons = player1.pokemons.updated( player1.currentPoke, Some( player1.pokemons.apply( player1.currentPoke ).get.changeHpInv( player1.pokemons.apply( player1.currentPoke ).get.pType.attacks.apply( attack ) ) ) ) ) )

  def calcDamage(pokemonArt1: PokemonArt, pokemonArt2: PokemonArt) : Double =
    pokemonArt1 match
      case PokemonArt.Wasser => pokemonArt2 match
          case PokemonArt.Wasser => 1
          case PokemonArt.Feuer => 1.2
          case PokemonArt.Blatt => 0.5
          case PokemonArt.Psycho => 1
      case PokemonArt.Feuer => pokemonArt2 match
          case PokemonArt.Wasser => 0.5
          case PokemonArt.Feuer => 1
          case PokemonArt.Blatt => 1.3
          case PokemonArt.Psycho => 1
      case PokemonArt.Blatt => pokemonArt2 match
          case PokemonArt.Wasser => 1.1
          case PokemonArt.Feuer => 1.3
          case PokemonArt.Blatt => 1
          case PokemonArt.Psycho => 1.2
      case PokemonArt.Psycho => pokemonArt2 match
          case PokemonArt.Wasser => 1
          case PokemonArt.Feuer => 1
          case PokemonArt.Blatt => 1
          case PokemonArt.Psycho => 0.7

  override def toString : String = mesh()

  