package com.example.event

import com.example.auth.AuthenticatedRequest
import com.example.repositories.ThingsRepository
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.parse
import io.circe.syntax._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class ThingsEventProcessor(request: AuthenticatedRequest, thingsRepository: ThingsRepository) extends EventProcessor {
  override def process(): Future[JsonString] = {
    val cursor = parse(request.body).getOrElse(Json.Null).hcursor
    val drop = cursor.downField("drop").as[Int].getOrElse(0)
    val limit = cursor.downField("limit").as[Int].getOrElse(10)
    thingsRepository.all(
      limit,
      drop
    ).map(ls => JsonString(ls.asJson.noSpaces))
  }
}


