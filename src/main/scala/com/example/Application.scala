package com.example

import com.amazonaws.services.lambda.runtime.Context
import com.example.auth.InMemoryAuthenticator
import com.example.event.EventProcessor
import com.example.lambda.{AwsHttpApiInputParser, AwsHttpApiResponse}
import com.example.repositories.slick.UserRepositoryImpl
import slick.jdbc.JdbcBackend.Database

import java.io.{InputStream, OutputStream}
import java.util.logging.Logger
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


class Application {

  lazy val db = Database.forConfig("prod")
  val inputParser = new AwsHttpApiInputParser()
  val responseWriter = new AwsHttpApiResponse()
  val authenticator = new InMemoryAuthenticator(new UserRepositoryImpl(db))
  val logger = Logger.getLogger("scala-aws-lambda")

  def start(in: InputStream, out: OutputStream, context: Context): Unit = {


    val fResult = for {
      parsedRequest <- inputParser.parseRequest(in)
      authenticatedRequest <- authenticator.authenticate(parsedRequest)
      eventProcess <- EventProcessor(authenticatedRequest, db)
      eventResponse <- eventProcess.process()
      _ <- responseWriter.build(out, eventResponse.value)
    } yield {
      ()
    }

    val finalF = fResult.recover {
      case e: Exception =>
        responseWriter.build(out, e)
    }
    Await.result(finalF, 60.seconds)
  }

}