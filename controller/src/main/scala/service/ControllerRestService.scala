package service

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpEntity, ContentTypes }
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import controller.ControllerInterface
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import scala.util.{ Failure, Success }
import play.api.libs.json.Json
import akka.stream.UniformFanInShape
import akka.stream.UniformFanOutShape
import akka.stream.scaladsl.Broadcast
import akka.stream.scaladsl.GraphDSL
import akka.stream.scaladsl.Merge
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.FlowShape
import akka.stream.scaladsl.GraphDSL.Builder
import akka.stream.scaladsl.{ Broadcast, Flow, GraphDSL, Merge, Sink, Source }
import akka.stream.{
  FlowShape,
  Materializer,
  UniformFanInShape,
  UniformFanOutShape
}
import akka.NotUsed
import scala.collection.immutable.LazyList.cons

class ControllerRestService( using controller: ControllerInterface ) {

  implicit val system: ActorSystem[?] =
    ActorSystem( Behaviors.empty, "SprayExample" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val controllerFlow: Flow[HttpRequest, String, NotUsed] =
    Flow.fromGraph( GraphDSL.create() { implicit builder: Builder[NotUsed] =>
      import GraphDSL.Implicits.*

      val bcast: UniformFanOutShape[HttpRequest, HttpRequest] =
        builder.add( Broadcast[HttpRequest]( 2 ) )

      val merge: UniformFanInShape[String, String] =
        builder.add( Merge[String]( 2 ) )

      val getReqFlow = Flow[HttpRequest].mapAsync( 1 ) { request =>
        request.uri.path.toString match {
          case "/state" =>
            Future.successful(
              HttpResponse(entity =
                Json
                  .obj( "state" -> controller.game.state.toString() )
                  .toString()
              )
            )

          case "/controller/game" =>
            Future.successful( gameResponse )
        }
      }

      val postReqFlow = Flow[HttpRequest].mapAsync( 1 ) { request =>
        request.uri.path.toString match {
          case "/controller/initPlayers" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.initPlayers )
                println( "test game:" + controller.game.toJson.toString() )
                gameResponse
              }
          case "/controller/addPlayer" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                val json = Json.parse( entity.data.utf8String )
                val msg = ( json \ "msg" ).as[String]
                controller
                  .doAndPublish( controller.addPlayer, msg )
                gameResponse
              }
          case "/controller/addPokemons" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                val json = Json.parse( entity.data.utf8String )
                val msg = ( json \ "msg" ).as[String]
                controller
                  .doAndPublish( controller.addPokemons, msg )
                gameResponse
              }
          case "/controller/next" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                val json = Json.parse( entity.data.utf8String )
                val msg = ( json \ "msg" ).as[String]
                controller
                  .doAndPublish( controller.nextMove, msg )
                gameResponse
              }
          case "/controller/attack" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                val json = Json.parse( entity.data.utf8String )
                val msg = ( json \ "msg" ).as[String]
                controller
                  .doAndPublish( controller.attackWith, msg )
                gameResponse
              }
          case "/controller/switchPokemon" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                val json = Json.parse( entity.data.utf8String )
                val msg = ( json \ "msg" ).as[String]
                controller
                  .doAndPublish( controller.selectPokemon, msg )
                gameResponse
              }
          case "/controller/gameOver" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.restartTheGame )
                gameResponse
              }
          case "/controller/save" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.save )
                gameResponse
              }
          case "/controller/load" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.load )
                gameResponse
              }
          case "/controller/undo" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.undoMove )
                gameResponse
              }
          case "/controller/redo" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.redoMove )
                gameResponse
              }
          case "/controller/update" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.updateGame )
                gameResponse
              }
          case "/controller/delete" =>
            request.entity
              .toStrict( Duration.apply( 3, TimeUnit.SECONDS ) )
              .map { entity =>
                controller.doAndPublish( controller.deleteGame )
                gameResponse
              }
        }
      }

      val getReqFlowShape = builder.add( getReqFlow )

      val getResponseFlow = Flow[HttpResponse].mapAsync( 1 ) { response =>
        Unmarshal( response.entity ).to[String]
      }

      val getResFlowShape = builder.add( getResponseFlow )
      val postRequestFlowShape = builder.add( postReqFlow )

      val postResponseFlow = Flow[HttpResponse].mapAsync( 1 ) { response =>
        Unmarshal( response.entity ).to[String]
      }
      val postResponseFlowShape = builder.add( postResponseFlow )

      bcast.out( 0 ) ~> getReqFlowShape ~> getResFlowShape ~> merge.in( 0 )
      bcast.out( 1 ) ~> postRequestFlowShape ~> postResponseFlowShape ~> merge
        .in( 1 )

      FlowShape( bcast.in, merge.out )
    } )

  val route: Route = {
    extractRequest { request =>
      complete(
        Source
          .single( request )
          .via( controllerFlow )
          .runWith( Sink.head )
          .map( response => response )
      )
    }
  }

  def start(): Unit = {
    val binding = Http().newServerAt( "0.0.0.0", 4001 ).bind( route )

    binding.onComplete {
      case Success( binding ) =>
        println(
          s"PokemonLite ControllerAPI service online at http://localhost:4001/"
        )
      case Failure( exception ) =>
        println(
          s"PokemonLite ControllerAPI service failed to start: ${exception.getMessage}"
        )
    }
  }

  def gameResponse =
    HttpResponse(entity =
      HttpEntity(
        ContentTypes.`application/json`,
        Json.obj( "game" -> controller.game.toJson ).toString()
      )
    )

}
