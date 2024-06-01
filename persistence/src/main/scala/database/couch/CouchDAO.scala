package database.couch


import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import spray.json._
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success, Try}
import play.api.libs.json.Json
import database.DAOInterface
import model.GameInterface
import model.impl.game.Game

object CouchDAO extends DAOInterface {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  case class CouchDBConfig(username: String, password: String, host: String, port: Int)

  val config = CouchDBConfig("username", "password", "localhost", 5984)
  val databaseName = "pokemonlite"

  def createDatabase(): Future[HttpResponse] = {
    val uri = s"http://${config.host}:${config.port}/$databaseName"
    val request = Put(uri).withHeaders(headers.Authorization(
      headers.BasicHttpCredentials(config.username, config.password)
    ))

    Http().singleRequest(request)
  }

  createDatabase().onComplete {
    case Success(response) if response.status == StatusCodes.Created =>
      println("Database created successfully")
    case Success(response) =>
      Unmarshal(response.entity).to[String].foreach(entity =>
        println(s"Database creation response: ${response.status}, $entity")
      )
    case Failure(exception) =>
      println(s"Failed to create database: $exception")
  }

  private var gameIdCounter: Option[Int] = Some(0)

  override def save(game: GameInterface): Try[Unit] = Try {
    val id = gameIdCounter.getOrElse(0)
    val gameJson = game.toJson

    val uri = s"http://${config.host}:${config.port}/$databaseName/$id"
    val request = Put(uri, HttpEntity(ContentTypes.`application/json`, gameJson.toString))
      .withHeaders(headers.Authorization(
        headers.BasicHttpCredentials(config.username, config.password)
      ))

    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

    responseFuture.onComplete {
      case Success(response) if response.status.isSuccess() =>
        println(s"Game $id saved successfully")
        gameIdCounter = Some(id + 1)
      case Success(response) =>
        Unmarshal(response.entity).to[String].foreach(entity =>
          println(s"Failed to save game $id: ${response.status}, $entity")
        )
      case Failure(exception) =>
        println(s"Request to save game $id failed: $exception")
    }
  }

  override def load(gameId: Option[Int] = None): Try[GameInterface] = Try {
    val id = gameId.getOrElse(gameIdCounter.getOrElse(0) - 1)
    val uri = s"http://${config.host}:${config.port}/$databaseName/$id"
    val request = Get(uri).withHeaders(headers.Authorization(
      headers.BasicHttpCredentials(config.username, config.password)
    ))

    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

    val result = responseFuture.flatMap { response =>
      if (response.status.isSuccess()) {
        val jsonFuture = Unmarshal(response.entity).to[String].map(Json.parse)
        jsonFuture.map(json => Game.fromJson(json))
      } else {
        Future.failed(new Exception(s"No game found with ID $id"))
      }
    }

    Await.result(result, Duration.Inf)
  }

  override def update(gameId: Option[Int], game: GameInterface): Try[Unit] = Try {
    val id = gameId.getOrElse(gameIdCounter.getOrElse(0))
    val gameJson = game.toJson

    val uri = s"http://${config.host}:${config.port}/$databaseName/$id"
    val request = Put(uri, HttpEntity(ContentTypes.`application/json`, gameJson.toString))
      .withHeaders(headers.Authorization(
        headers.BasicHttpCredentials(config.username, config.password)
      ))

    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

    responseFuture.onComplete {
      case Success(response) if response.status.isSuccess() =>
        println(s"Game $id updated successfully")
      case Success(response) =>
        Unmarshal(response.entity).to[String].foreach(entity =>
          println(s"Failed to update game $id: ${response.status}, $entity")
        )
      case Failure(exception) =>
        println(s"Request to update game $id failed: $exception")
    }
  }

  override def delete(gameId: Option[Int]): Try[Unit] = Try {
    val id = gameId.getOrElse(gameIdCounter.map(_ - 1).getOrElse(0))
    val uri = s"http://${config.host}:${config.port}/$databaseName/$id"

    val fetchRequest = Get(uri).withHeaders(headers.Authorization(
      headers.BasicHttpCredentials(config.username, config.password)
    ))

    val fetchResponseFuture: Future[HttpResponse] = Http().singleRequest(fetchRequest)

    val fetchResult = fetchResponseFuture.flatMap { response =>
      if (response.status.isSuccess()) {
        Unmarshal(response.entity).to[String]
      } else {
        Future.failed(new Exception(s"No game found with ID $id"))
      }
    }

    val fetchResultString = Await.result(fetchResult, Duration.Inf)
    val fetchResultJson = Json.parse(fetchResultString)
    val rev = (fetchResultJson \ "_rev").as[String]

    val deleteUri = s"$uri?rev=$rev"
    val deleteRequest = Delete(deleteUri).withHeaders(headers.Authorization(
      headers.BasicHttpCredentials(config.username, config.password)
    ))

    val deleteResponseFuture: Future[HttpResponse] = Http().singleRequest(deleteRequest)

    deleteResponseFuture.onComplete {
      case Success(response) if response.status.isSuccess() =>
        println(s"Game $id deleted successfully")
      case Success(response) =>
        Unmarshal(response.entity).to[String].foreach(entity =>
          println(s"Failed to delete game $id: ${response.status}, $entity")
        )
      case Failure(exception) =>
        println(s"Request to delete game $id failed: $exception")
    }
  }
}