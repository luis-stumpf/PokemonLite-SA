package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ErrorSpec extends AnyWordSpec {
  "A Error" when {
    "No Input could be detected" should {
      "throw a No Input Message" in {
        val error = NoInput
        error.toString should be("Error: Please Enter a valid input.")
      }
    }
    "a Name is too long" should {
      "throw a message with the wrong input" in {
        val error = NameTooLong("zuLangerName")
        error.toString should be("Error: The Name \"zuLangerName\" is too long.")
        error.equals(NoInput) should be(false)
        error.equals(NameTooLong("zuLangerName")) should be(true)
      }
    }
    val error = WrongInput("hello")
    error.input should be("hello")
  }
}
