package com.towncrier.event

import com.towncrier.auth.AuthenticatedRequest
import com.towncrier.http.LoginRequestBody
import com.towncrier.repositories.UserRepository
import io.circe.generic.auto._
import io.circe.parser._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LoginEventProcessor(request: AuthenticatedRequest, userRepository: UserRepository) extends EventProcessor {
  override def process(): Future[JsonString] = {
    val r = for {
      requestData <- decode[LoginRequestBody](request.body)
        .fold(e => Future.failed(e.getCause), s => Future.successful(s))
       user <- userRepository.findBy(requestData.mobile, requestData.pwd)
    } yield user

    r.flatMap({
      case Some(u) =>
        Future.successful(JsonString(u.token))
      case None =>
        Future.failed(new Exception(s"Invalid mobile or pwd"))
    })

  }
}

