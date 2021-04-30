package com.example.auth

import com.example.lambda.Request
import com.example.repositories.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Authenticator {
  def authenticate(request: Request): Future[AuthenticatedRequest]
}


class InMemoryAuthenticator(userRepository: UserRepository) extends Authenticator {
  override def authenticate(request: Request): Future[AuthenticatedRequest] =
    request.eventType match {
      case "login" =>
        Future.successful(AuthenticatedRequest(
          user = User(0, "fname", "fname", ""),
          body = request.body,
          eventType = request.eventType,
          clientIp = request.clientIp
        ))
      case _ =>
        request.token match {
          case None => Future.failed(new Exception("token not found"))
          case Some(token) =>
            userRepository
              .findByToken(token)
              .flatMap({
                case None => Future.failed(new Exception("No user found for token"))
                case Some(repoUser) =>
                  Future.successful(AuthenticatedRequest(
                    user = User(repoUser.id, repoUser.fName, repoUser.lName, repoUser.mobile),
                    body = request.body,
                    eventType = request.eventType,
                    clientIp = request.clientIp
                  ))

              })
        }
    }
}