package com.example.lambda

import io.circe._
import io.circe.parser._

import java.io.InputStream
import java.util.Base64
import scala.concurrent.Future


trait LambdaInputParser {
  def parseRequest(in: InputStream): Future[Request]
}

class AwsHttpApiInputParser() extends LambdaInputParser {
  override def parseRequest(in: InputStream): Future[Request] = {
    val jsonString = scala.io.Source.fromInputStream(in).mkString
    val cursor = parse(jsonString).getOrElse(Json.Null).hcursor

    val result = for {
      body <- cursor.downField("body").as[String]
      base64Decode = decodeBase64(body)
      bodyCursor = parse(base64Decode).getOrElse(Json.Null).hcursor
      sourceIp <- cursor
        .downField("requestContext")
        .downField("http")
        .downField("sourceIp")
        .as[String]
      token = bodyCursor
        .downField("authorization")
        .as[String]
        .map(Option(_)).getOrElse(None)
      eventType <- bodyCursor
        .downField("eventType")
        .as[String]
    } yield

      Request(
        base64Decode,
        eventType,
        token,
        sourceIp
      )

    result.fold(
      error => Future.failed(new Exception(error.toString)),
      success => Future.successful(success)
    )
  }

  private def decodeBase64(str: String): String = {
    import java.util.Base64
    val decodedBytes: Array[Byte] = Base64.getDecoder.decode(str)
    new String(decodedBytes)
  }
}