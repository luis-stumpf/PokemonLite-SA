package de.htwg.se.pokelite

@main def run() : Unit =
  println(screen)


def screen: String = row() + col() + row()
def row  (width : Int = 60): String = "+"+("-"*width+"+")*2+"\n"
def col (height: Int = 14): String = (("|"+" "*60)*2+"|\n")*height