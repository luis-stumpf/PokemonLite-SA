package de.htwg.se.pokelite
package model

case class PokePack[T](val contents:List[T]){
  def map(f:T => T) = contents.map(pokemon => f(pokemon))
}
