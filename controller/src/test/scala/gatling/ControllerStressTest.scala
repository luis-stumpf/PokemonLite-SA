package gatling

import scala.concurrent.duration.*
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ControllerStressTest extends SimulationSkeleton {

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
    var scn1 = buildScenario( "Scenario 1 to much" )
    var scn2 = buildScenario( "Scenario 1 okey" )

    setUp(
      // load test with only one user requesting a normal amount of requests
      scn1.inject( stressPeakUsers( 10000 ) during ( 20.second ) )
    ).protocols( httpProtocol )
  }

  executeOperations()

}
