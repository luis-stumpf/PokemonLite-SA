package gatling

import scala.concurrent.duration.*
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PersistenceVolumeTest extends SimulationSkeleton {

  override val operations = List(
    buildOperation( "root", "GET", "/", StringBody( "" ) ),
    buildOperation( "save", "POST", "/save", saveGame ),
    buildOperation( "load", "GET", "/load", StringBody( "" ) )
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
            constantUsersPerSec( 500 ) during ( 10.second )
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
