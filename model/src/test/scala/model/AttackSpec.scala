package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class AttackSpec extends AnyWordSpec {
  "No Attack" should {
    val noAttack = NoAttack()
    "have a String of form ''" in {
      noAttack.toString should be( "" )
    }
  }
  "An Attack" should {
    val attack1 = Attack( "Tackle", 30 )
    val attack2 = Attack( "Slam", 10 )
    val attack3 = Attack( "Flash", 50 )
    "have a String of form 'name'" in {
      attack1.toString should be( "Tackle" )
      attack2.toString should be( "Slam" )
      attack3.toString should be( "Flash" )
    }
    "have a attack value" in {
      attack1.damage should be( 30 )
      attack2.damage should be( 10 )
      attack3.damage should be( 50 )
    }
    "have toJson method" in {
      val json1 = attack1.toJson
      val json2 = attack2.toJson
      val json3 = attack3.toJson

      json1.toString() should be( """{"name":"Tackle","damage":30}""" )
      json2.toString() should be( """{"name":"Slam","damage":10}""" )
      json3.toString() should be( """{"name":"Flash","damage":50}""" )
    }
  }

}
