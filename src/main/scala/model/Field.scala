package de.htwg.se.pokelite.model

@main def run() : Unit =
  println(mesh(60, 60))

def mesh (width : Int, height : Int): String = row(width) + col(width, height) + row(width)
def row  (width : Int): String = "+"+("-"*width+"+")*2+"\n"
def col (width : Int, height: Int): String = (("|"+" "*width)*2+"|\n")*height

