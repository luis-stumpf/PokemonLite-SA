package de.htwg.se.pokelite
package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ErrorSpec extends AnyWordSpec {
  "A Error" when {
    val error = WrongInput("hello")
    error.input should be("hello")
  }
}
