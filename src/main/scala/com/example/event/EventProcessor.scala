package com.example.event

import com.example.auth.AuthenticatedRequest
import com.example.repositories.slick.{ThingsRepositoryImpl, UserRepositoryImpl}
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Future

case class JsonString(value: String)

trait EventProcessor {
  def process(): Future[JsonString]
}

object EventProcessor {


  def apply(request: AuthenticatedRequest, db: Database): Future[EventProcessor] = request.eventType match {
    case "login" =>
      Future.successful(new LoginEventProcessor(request, new UserRepositoryImpl(db)))
    case "things" =>
      Future.successful(new ThingsEventProcessor(request, new ThingsRepositoryImpl(db)))
    case _ =>
      throw new RuntimeException(s"Invalid event type")
  }
}