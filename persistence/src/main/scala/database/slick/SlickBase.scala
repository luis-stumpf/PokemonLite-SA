package database.slick

import scala.util.control.Breaks.{ break, breakable }
import scala.concurrent.Await
import scala.util.{ Try, Success, Failure }
import scala.concurrent.duration.DurationInt
import slick.jdbc.JdbcBackend.Database
import slick.dbio.DBIOAction
import slick.dbio.NoStream
import slick.dbio.Effect

trait SlickBase:

  val db = Database.forURL(
    url = "jdbc:postgresql://localhost:6432/tbl",
    user = "postgres",
    password = "postgres",
    driver = "org.postgresql.Driver"
  )

  protected val connectionRetryAttempts = 10
  protected val maxWaitSeconds = 5.seconds
  protected val currentGameId = 1

  protected def init( setup: DBIOAction[Unit, NoStream, Effect.Schema] ): Unit =
    println( "Connecting to DB..." )
    breakable {
      for (i <- 1 to connectionRetryAttempts)
        Try( Await.result( db.run( setup ), maxWaitSeconds ) ) match
          case Success( _ ) => println( "DB connection established" ); break
          case Failure( e ) =>
            if e.getMessage.contains( "Multiple primary key defined" )
            then // ugly workaround: https://github.com/slick/slick/issues/1999
              println( "Assuming DB connection established" )
              break
            println(
              s"DB connection failed - retrying... - $i/$connectionRetryAttempts"
            )
            println( e.getMessage )
            Thread.sleep( maxWaitSeconds.toMillis )
    }
