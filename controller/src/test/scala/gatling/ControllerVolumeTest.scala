package gatling

import scala.concurrent.duration.*
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ControllerVolumeTest extends SimulationSkeleton {

  override val operations = List(
    buildOperation( "State", "GET", "/state", StringBody( "" ) ),
    buildOperation( "State", "GET", "/state", StringBody( "" ) ),
    buildOperation(
      "add player1",
      "POST",
      "/controller/addPlayer?input=Luis",
      StringBody( "" )
    ),
    buildOperation(
      "add player2",
      "POST",
      "/controller/addPlayer?input=Luis",
      StringBody( "" )
    ),
    buildOperation(
      "addpokemons1",
      "POST",
      "/controller/addPokemons?input=111",
      StringBody( "" )
    ),
    buildOperation(
      "addpokemons2",
      "POST",
      "/controller/addPokemons?input=111",
      StringBody( "" )
    ),
    buildOperation(
      "save",
      "POST",
      "/controller/save?input=1",
      StringBody( "" )
    ),
    buildOperation(
      "load",
      "POST",
      "/controller/save?input=1",
      StringBody( "" )
    ),
    buildOperation(
      "load",
      "POST",
      "/controller/next?input=1",
      StringBody( "" )
    ),
    buildOperation(
      "attack",
      "POST",
      "/controller/attack?input=1",
      StringBody( "" )
    ),
    buildOperation(
      "undo",
      "POST",
      "/controller/undo?input=1",
      StringBody( "" )
    ),
    buildOperation(
      "redo",
      "POST",
      "/controller/redo?input=1",
      StringBody( "" )
    )
  )

  override def executeOperations(): Unit = {
    var scn1 = buildScenario( "Scenario 1" )
    var scn2 = buildScenario( "Scenario 2" )
    var scn3 = buildScenario( "Scenario 3" )

    setUp(
      // load test with only one user requesting a normal amount of requests
      scn1
        .inject(
          // ramp up users to 100 in 10 seconds
          rampUsersPerSec( 10 ) to 100 during ( 10.second )
        )
        .andThen(
          scn2.inject(
            // hold 100 users for 10 seconds
            constantUsersPerSec( 100 ) during ( 10.second )
          )
        )
        .andThen(
          scn3.inject(
            // ramp down users to 0 in 10 seconds
            rampUsersPerSec( 100 ) to 0 during ( 10.second )
          )
        )
    ).protocols( httpProtocol )
  }

  executeOperations()

}
