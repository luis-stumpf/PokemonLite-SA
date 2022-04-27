package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class AttackSpec extends AnyWordSpec {
  "A Attack" should {
    val noAttack = NoAttack()
    "have a String of form ''" in {
      noAttack.toString should be("")
    }
  }
  "A Attack" should {
    val attack = Attack("Tackle", 30)
    "have a String of form 'name'" in {
      attack.toString should be("Tackle")
    }
  }

}
