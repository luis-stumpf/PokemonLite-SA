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
    val attack1 = Attack("Tackle", 30)
    val attack2 = Attack("Slam", 10)
    val attack3 = Attack("Flash", 50)
    "have a String of form 'name'" in {
      attack1.toString should be("Tackle")
      attack2.toString should be("Slam")
      attack3.toString should be("Flash")
    }
    "have a attack value" in{
      attack1.damage should be(30)
      attack2.damage should be(10)
      attack3.damage should be(50)
    }
  }

}