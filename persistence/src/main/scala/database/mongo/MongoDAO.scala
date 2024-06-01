package database.mongo

import database.DAOInterface
import model.GameInterface
import model.impl.game.Game
import fileIo.json.FileIOJson
import org.mongodb.scala.*
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.bson.Document
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Projections.excludeId
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}
import com.mongodb.client.model.UpdateOptions
import org.mongodb.scala.SingleObservable
import org.bson.json.JsonWriterSettings
import play.api.libs.json.Json
import play.api.libs.json.JsValue

import scala.concurrent.Await
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.duration.DurationInt
import scala.concurrent.Promise
import scala.concurrent.Future

object MongoDAO extends DAOInterface {

 // private val database_username = sys.env.getOrElse("MONGO_INITDB_ROOT_USERNAME", "root")
 // private val database_pw = sys.env.getOrElse("MONGO_INITDB_ROOT_PASSWORD", "root")
 // private val databaseUrl: String = s"mongodb://$database_username:$database_pw@mongo:27017/?authSource=admin"

  //println("Database URL: " + databaseUrl)


  // private val client: MongoClient = MongoClient("mongodb://127.0.0.1:27017")
  //private val client: MongoClient = MongoClient("mongodb://root:root@mongodb:27017/?authSource=admin")
  private val client: MongoClient = MongoClient("mongodb://localhost:27017")
  //private val client: MongoClient = MongoClient(databaseUrl)
  private val db: MongoDatabase = client.getDatabase("PokemonLite")
  private val gameCollection: MongoCollection[Document] = db.getCollection("game")

  val ping = db.runCommand(Document("ping" -> 1)).head()
  Await.result(ping, 10.seconds)
  System.out.println("Pinged your deployment. You successfully connected to MongoDB!")

  private val maxWaitSeconds: Duration = 5 seconds
  private var gameIdCounter: Option[Int] = Some(0)

  override def save(game: GameInterface): Try[Unit] = {
    println("Saving game in MongoDB")
    Try {
      update(gameIdCounter, game) match
        case Success(_) => gameIdCounter .map(_ + 1)
        case Failure(exception) => throw exception
    }
  }

  override def load(gameId: Option[Int] = None): Try[GameInterface] = {
    println("Loading game from MongoDB")
    Try {
      val searchId = gameId.getOrElse(gameIdCounter.getOrElse(0))
      val document = Await.result(
        gameCollection
          .find(equal("_id", searchId)).projection(excludeId())
          .first().head(), 5.seconds)
      val gameString = document.get("game").get.asString().getValue
      val gameJson = Json.parse(gameString)
      val game = Game.fromJson(gameJson)
      println("Game loaded")
      game
    }
  }

  override def update(gameId: Option[Int], game: GameInterface): Try[Unit] = {
    println("Updating game in MongoDB")

    Try {
      val updateObservable = gameCollection
        .updateOne(equal("_id", gameId.getOrElse(gameIdCounter.getOrElse(0))),
          set("game", game.toJson.toString), UpdateOptions().upsert(true))

      observerUpdate(updateObservable)
    }
  }

  override def delete(gameId: Option[Int]): Try[Unit] = {
    println("Deleting game from MongoDB")

    Try {
      val deleteId = gameId.getOrElse({
        Await.result(gameCollection.find().sort(Document("_id" -> -1)).first().head(),
          maxWaitSeconds).get("_id").get.asInt32().getValue
      })

      Await.result(
        gameCollection.deleteOne(equal("_id", deleteId))
          .asInstanceOf[SingleObservable[Unit]]
          .head(),
        maxWaitSeconds
      )
      println("Game deleted")
    }
  }

  private def observerUpdate(insertObservable: SingleObservable[UpdateResult]): Unit = {
    insertObservable.subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = println(s"updated: $result")

      override def onError(e: Throwable): Unit = println(s"update onError: $e")

      override def onComplete(): Unit = println("completed update")
    })
  }
}