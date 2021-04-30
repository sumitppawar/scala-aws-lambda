package com.example.lambda

import io.circe.generic.auto._
import io.circe.syntax._

import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8
import java.util.Base64
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
private[this] case class Response(
                     isBase64Encoded: Boolean,
                     statusCode: Int,
                     body: String,
                     headers: Map[String, String]
                   )

trait LambdaResponse {
    def build(out: OutputStream, body: String): Future [Unit]
    def build(out: OutputStream, error: Throwable): Unit
}

class AwsHttpApiResponse extends LambdaResponse {
  override def build(out: OutputStream, body:String): Future [Unit] =  Future {
    val bodyBase64 = Base64.getEncoder.encodeToString(body.getBytes(StandardCharsets.UTF_8))
    val reply = Response(
      isBase64Encoded = false,
      statusCode = 200,
      body = bodyBase64,
      Map("Content-Type" -> "application/json")
    )

      out.write(reply.asJson.noSpaces.getBytes(UTF_8))
  }

  def build(out: OutputStream, error: Throwable): Unit =  {
    val bodyBase64 = Base64.getEncoder.encodeToString(error.getMessage.getBytes(StandardCharsets.UTF_8))
    val reply = Response(
      isBase64Encoded = false,
      statusCode = 500,
      body = s"$bodyBase64",
      Map("Content-Type" -> "application/json")
    )
    print(s"${reply.asJson.noSpaces}")
    out.write(reply.asJson.noSpaces.getBytes(UTF_8))
  }

}
