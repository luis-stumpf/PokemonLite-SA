package de.htwg.se.pokelite.model

@main def run() : Unit =
  println(mesh)

def mesh (width : Int =  60, height : Int = 60): String = row(width) + col(width, height) + row(width)
def row  (width : Int = 50): String = "+"+("-"*width+"+")*2+"\n"
def col (width : Int = 50, height: Int = 3): String = (("|"+" "*width)*2+"|\n")*height

