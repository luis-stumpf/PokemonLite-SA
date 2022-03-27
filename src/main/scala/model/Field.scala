package de.htwg.se.pokelite.model

@main def run() : Unit =
  println(screen)


def screen: String = row() + col() + colPokemonP1(Pokemon.G) + col() + colPokemonP2(Pokemon.S) + col() + row()
def row  (width : Int = 60): String = "+"+("-"*width+"+")*2+"\n"
def col (height: Int = 4): String = (("|"+" "*60)*2+"|\n")*height
def colPokemonP1 (pokemon: Pokemon): String = "|"+" "*45 + pokemon + " "*(15-pokemon.toString.length) + "|" + " "*60 + "|" +"\n" +
  "|"+" "*47 + pokemon.getHp + " "*(13-pokemon.getHp.toString.length) + "|" + " "*60 + "|" +"\n"
def colPokemonP2 (pokemon: Pokemon): String = "|"+" "*5 + pokemon + " "*(55-pokemon.toString.length) + "|" + " "*60 + "|" +"\n" +
  "|"+" "*7 + pokemon.getHp + " "*(53-pokemon.getHp.toString.length) + "|" + " "*60 + "|" +"\n"
