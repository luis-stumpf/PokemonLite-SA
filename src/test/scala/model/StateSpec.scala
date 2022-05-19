package de.htwg.se.pokelite
package model
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class StateSpec extends AnyWordSpec{
  "A State" should {
    val preState = PreState(Field(50, PokePlayer("",1), PokePlayer("",2)))
    val midState = MidState(Field(50, PokePlayer("",1), PokePlayer("",2)))
    val endState = EndState(Field(50, PokePlayer("",1), PokePlayer("",2)))

    "preState string" in {
      preState.toString should be("PreGame!\n")
    }
    "midState string" in {
      midState.toString should be("MidGame!\n")
    }
    "endState string" in {
      endState.toString should be("EndGame!\n")
    }
  }
}
