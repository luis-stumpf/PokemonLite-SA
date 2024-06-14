package gatling

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.util.Random
import io.gatling.core.structure.ChainBuilder
import io.gatling.core.body.Body

abstract class SimulationSkeleton extends Simulation {

  val saveGame = StringBody(
    """{"state":{"stateVal":"DesicionState"},"player1":{"name":"luis","pokemons":{"contents":[{"pType":"Glurak","hp":150,"isDead":false,"maxHp":150},{"pType":"Simsala","hp":130,"isDead":false,"maxHp":130},{"pType":"Brutalanda","hp":170,"isDead":false,"maxHp":170}],"size":3},"currentPoke":0},"player2":{"name":"timmy","pokemons":{"contents":[{"pType":"Brutalanda","hp":170,"isDead":false,"maxHp":170},{"pType":"Simsala","hp":130,"isDead":false,"maxHp":130},{"pType":"Glurak","hp":150,"isDead":false,"maxHp":150}],"size":3},"currentPoke":0},"turn":1,"winner":{}}"""
  )

  def executeOperations(): Unit

  val operations: List[ChainBuilder]

  val httpProtocol = http
    .baseUrl( "http://localhost:4002" )
    .inferHtmlResources()
    .acceptHeader(
      "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8"
    )
    .acceptEncodingHeader( "gzip, deflate, br" )
    .acceptLanguageHeader( "en-GB,en-US;q=0.9,en;q=0.8" )
    .doNotTrackHeader( "1" )
    .userAgentHeader(
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36"
    )

  private val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Cache-Control" -> "no-cache",
    "Pragma" -> "no-cache",
    "Sec-Fetch-Dest" -> "document",
    "Sec-Fetch-Mode" -> "navigate",
    "Sec-Fetch-Site" -> "none",
    "Sec-Fetch-User" -> "?1",
    "Upgrade-Insecure-Requests" -> "1",
    "sec-ch-ua" -> """Chromium";v="125", "Not.A/Brand";v="24""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> "macOS"
  )

  private val headers_1 = Map(
    "Cache-Control" -> "no-cache",
    "Pragma" -> "no-cache",
    "Sec-Fetch-Dest" -> "image",
    "Sec-Fetch-Mode" -> "no-cors",
    "Sec-Fetch-Site" -> "same-origin",
    "sec-ch-ua" -> """Chromium";v="125", "Not.A/Brand";v="24""",
    "sec-ch-ua-mobile" -> "?0",
    "sec-ch-ua-platform" -> "macOS"
  )

  private val scn = scenario( "RecordedSimulation" )
    .exec(
      http( "request_0" )
        .get( "/state" )
        .headers( headers_0 )
        .resources(
          http( "request_1" )
            .get( "/favicon.ico" )
            .headers( headers_1 )
            .check( status.is( 404 ) )
        )
    )

  def buildOperation(
    name: String,
    request: String,
    operation: String,
    body: Body
  ): ChainBuilder = {
    exec(
      http( name )
        .httpRequest( request, operation )
        .body( body )
    )
  }

  def buildScenario( name: String ) =
    scenario( name )
      .exec(
        // exec the operations and between each pause a second
        operations.reduce( ( a, b ) => a.pause( 1.second ).exec( b ) )
      )

}
